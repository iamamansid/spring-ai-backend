package com.iamamansid.spring_ai_backend.Service.Impl;

import com.iamamansid.spring_ai_backend.Service.EntityExtractionService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class EntityExtractionServiceImpl implements EntityExtractionService {

    @Override
    public Map<String, List<String>> extractEntities(String text) {
        Map<String, List<String>> entities = new HashMap<>();

        entities.put("DATE", extractDates(text));
        entities.put("AMOUNT", extractAmounts(text));
        entities.put("INVOICE_NO", extractInvoiceNumbers(text));
        entities.put("COMPANY", extractCompanies(text));

        return entities;
    }

    public List<String> extractDates(String text) {
        Set<String> dates = new HashSet<>();

        // Matches: 12.03.2018, 25.11.2023, 12/03/2018
        Pattern datePattern = Pattern.compile(
                "\\b(0?[1-9]|[12][0-9]|3[01])[./-](0?[1-9]|1[0-2])[./-](19|20)\\d{2}\\b"
        );

        Matcher matcher = datePattern.matcher(text);
        while (matcher.find()) {
            dates.add(matcher.group());
        }

        return new ArrayList<>(dates);
    }

    /* ---------------- AMOUNT EXTRACTION ---------------- */

    public List<String> extractAmounts(String text) {
        Set<String> amounts = new HashSet<>();

        // Matches: 85.00, 765.00, 1,250.50
        Pattern amountPattern = Pattern.compile(
                "\\b\\d{1,3}(,\\d{3})*(\\.\\d{2})\\b"
        );

        Matcher matcher = amountPattern.matcher(text);
        while (matcher.find()) {
            amounts.add(matcher.group());
        }

        return new ArrayList<>(amounts);
    }

    /* ---------------- INVOICE NUMBER EXTRACTION ---------------- */

    public List<String> extractInvoiceNumbers(String text) {
        Set<String> invoiceNumbers = new HashSet<>();

        // Matches: Invoice Number: #ABC98765432100
        Pattern invoicePattern = Pattern.compile(
                "(?i)(invoice\\s*(number|no|#)[:\\s]*[#]?[A-Z0-9\\-]+)"
        );

        Matcher matcher = invoicePattern.matcher(text);
        while (matcher.find()) {
            invoiceNumbers.add(matcher.group().trim());
        }

        return new ArrayList<>(invoiceNumbers);
    }

    /* ---------------- COMPANY EXTRACTION ---------------- */

    public List<String> extractCompanies(String text) {
        Set<String> companies = new HashSet<>();

        // Common invoice/company indicators
        List<String> companyKeywords = Arrays.asList(
                "Company:", "Bank Name:", "Account Name:"
        );

        for (String keyword : companyKeywords) {
            Pattern pattern = Pattern.compile(
                    Pattern.quote(keyword) + "\\s*([A-Z][A-Za-z0-9&.,\\s]+)"
            );
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                companies.add(matcher.group(1).trim());
            }
        }

        return companies.stream()
                .filter(name -> name.length() > 3)
                .collect(Collectors.toList());
    }


}
