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

    public boolean createInvoiceGraph(Map<String, List<String>> entities) {

        String invoiceNo = entities.get("INVOICE_NO").get(0)
                .replaceAll(".*#", "")
                .trim();

        try (Session session = driver.session()) {

            return session.executeWrite(tx -> {

                // 1️⃣ Invoice (single source of truth)
                tx.run(
                        """
                        MERGE (i:Invoice {invoiceNo: $invoiceNo})
                        ON CREATE SET i.isGroundTruth = false
                        """,
                        Map.of("invoiceNo", invoiceNo)
                );

                // 2️⃣ Dates (invoice-scoped ONLY)
                for (String date : entities.getOrDefault("DATE", List.of())) {
                    tx.run(
                            """
                            MATCH (i:Invoice {invoiceNo: $invoiceNo})
                            MERGE (d:Date {date: $date})
                            MERGE (i)-[:HAS_DATE]->(d)
                            """,
                            Map.of("invoiceNo", invoiceNo, "date", date)
                    );
                }

                // 3️⃣ Amounts (invoice-scoped ONLY)
                for (String amount : entities.getOrDefault("AMOUNT", List.of())) {
                    tx.run(
                            """
                            MATCH (i:Invoice {invoiceNo: $invoiceNo})
                            MERGE (a:Amount {amount: $amount})
                            MERGE (i)-[:HAS_AMOUNT]->(a)
                            """,
                            Map.of("invoiceNo", invoiceNo, "amount", amount)
                    );
                }

                // 4️⃣ Companies (global, normalized)
                for (String company : entities.getOrDefault("COMPANY", List.of())) {

                    String normalizedCompany =
                            company.trim().replaceAll("\\s+", " ");

                    tx.run(
                            """
                            MATCH (i:Invoice {invoiceNo: $invoiceNo})
                            MERGE (c:Company {name: $company})
                            MERGE (i)-[:ISSUED_TO]->(c)
                            """,
                            Map.of(
                                    "invoiceNo", invoiceNo,
                                    "company", normalizedCompany
                            )
                    );
                }

                // 5️⃣ Verification
                Result r = tx.run(
                        """
                        MATCH (i:Invoice {invoiceNo:$invoiceNo})
                        RETURN count(i) AS cnt
                        """,
                        Map.of("invoiceNo", invoiceNo)
                );

                return r.single().get("cnt").asInt() == 1;
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

    @Override
    public long getTotalNodeCount(String invoiceNo) {
        try (Session session = driver.session()) {
            Result result = session.run(
                    """
                    MATCH (i:Invoice {invoiceNo:$invoiceNo})-[*0..1]-(n)
                    RETURN count(DISTINCT n) AS count
                    """,
                    Map.of("invoiceNo", invoiceNo)
            );
            return result.single().get("count").asLong();
        }
    }


    @Override
    public boolean checkIdempotency(
            String invoiceNo,
            Runnable reIngestOperation) {

        long before = getTotalNodeCount(invoiceNo);

        reIngestOperation.run();

        long after = getTotalNodeCount(invoiceNo);

        return before == after;
    }


    public boolean queryInvoiceByCompany(String companyName) {
        try (Session session = driver.session()) {
            Result result = session.run(
                    """
                    MATCH (i:Invoice)-[:ISSUED_TO]->(c:Company {name:$company})
                    RETURN i LIMIT 1
                    """,
                    Map.of("company", companyName)
            );
            return result.hasNext();
        }
    }

    public boolean queryInvoiceByAmount(String amount) {
        try (Session session = driver.session()) {
            Result result = session.run(
                    """
                    MATCH (i:Invoice)-[:HAS_AMOUNT]->(a:Amount {value:$amount})
                    RETURN i LIMIT 1
                    """,
                    Map.of("amount", amount)
            );
            return result.hasNext();
        }
    }

    public boolean queryInvoiceExists(String invoiceNo) {
        try (Session session = driver.session()) {
            Result result = session.run(
                    """
                    MATCH (i:Invoice {invoiceNo:$invoiceNo})
                    RETURN i
                    """,
                    Map.of("invoiceNo", invoiceNo)
            );
            return result.hasNext();
        }
    }

}

