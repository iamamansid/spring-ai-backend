package com.iamamansid.spring_ai_backend.Service.Impl;

import com.iamamansid.spring_ai_backend.Service.GroundTruthService;
import com.iamamansid.spring_ai_backend.models.response.GroundTruthInvoice;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GroundTruthServiceImpl implements GroundTruthService {
    private final Driver driver;

    public GroundTruthServiceImpl(Driver driver) {
        this.driver = driver;
    }

    public GroundTruthInvoice loadGroundTruth(String invoiceNo) {

        try (Session session = driver.session()) {

            Result result = session.run(
                    """
                    MATCH (i:Invoice {invoiceNo: $invoiceNo})
                    OPTIONAL MATCH (i)-[:HAS_DATE]->(d:Date)
                    OPTIONAL MATCH (i)-[:HAS_AMOUNT]->(a:Amount)
                    OPTIONAL MATCH (i)-[:ISSUED_TO]->(c:Company)
                    RETURN 
                        i.invoiceNo AS invoiceNo,
                        collect(DISTINCT d.value) AS dates,
                        collect(DISTINCT a.value) AS amounts,
                        collect(DISTINCT c.name) AS companies
                    """,
                    Map.of("invoiceNo", invoiceNo)
            );

            if (!result.hasNext()) {
                throw new RuntimeException(
                        "Ground truth not found for invoice: " + invoiceNo
                );
            }

            Record record = (Record) result.next();

            GroundTruthInvoice gt = new GroundTruthInvoice();
            gt.setInvoiceNo(record.get("invoiceNo").asString());
            gt.setDates(record.get("dates").asList(Value::asString));
            gt.setAmounts(record.get("amounts").asList(Value::asString));
            gt.setCompanies(record.get("companies").asList(Value::asString));

            return gt;
        }
    }
}
