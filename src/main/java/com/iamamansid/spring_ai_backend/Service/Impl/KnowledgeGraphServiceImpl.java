package com.iamamansid.spring_ai_backend.Service.Impl;

import com.iamamansid.spring_ai_backend.Service.KnowledgeGraphService;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class KnowledgeGraphServiceImpl implements KnowledgeGraphService {

    private final Driver driver;

    public KnowledgeGraphServiceImpl(Driver driver) {
        this.driver = driver;
    }

    public void createInvoiceGraph(Map<String, List<String>> entities) {

        String rawInvoice = entities.get("INVOICE_NO").get(0);
        String invoiceNo = rawInvoice.replaceAll(".*#", "").trim();

        try (Session session = driver.session()) {
            session.executeWrite(tx -> {

                // 1️⃣ Create Invoice node
                tx.run(
                        "MERGE (i:Invoice {invoiceNo: $invoiceNo})",
                        Map.of("invoiceNo", invoiceNo)
                );

                // 2️⃣ Dates
                for (String date : entities.getOrDefault("DATE", List.of())) {
                    tx.run(
                            """
                            MERGE (d:Date {value: $date})
                            MERGE (i:Invoice {invoiceNo: $invoiceNo})
                            MERGE (i)-[:HAS_DATE]->(d)
                            """,
                            Map.of("date", date, "invoiceNo", invoiceNo)
                    );
                }

                // 3️⃣ Amounts
                for (String amount : entities.getOrDefault("AMOUNT", List.of())) {
                    tx.run(
                            """
                            MERGE (a:Amount {value: $amount})
                            MERGE (i:Invoice {invoiceNo: $invoiceNo})
                            MERGE (i)-[:HAS_AMOUNT]->(a)
                            """,
                            Map.of("amount", amount, "invoiceNo", invoiceNo)
                    );
                }

                // 4️⃣ Companies
                for (String company : entities.getOrDefault("COMPANY", List.of())) {
                    tx.run(
                            """
                            MERGE (c:Company {name: $company})
                            MERGE (i:Invoice {invoiceNo: $invoiceNo})
                            MERGE (i)-[:ISSUED_TO]->(c)
                            """,
                            Map.of("company", company, "invoiceNo", invoiceNo)
                    );
                }

                return null;
            });
        }
    }

    public boolean executeTestQuery(String companyName) {

        try (Session session = driver.session()) {
            Result result = session.run(
                    """
                    MATCH (i:Invoice)-[:ISSUED_TO]->(c:Company {name:$company})
                    RETURN i
                    """,
                    Map.of("company", companyName)
            );
            return result.hasNext();
        }
    }
}

