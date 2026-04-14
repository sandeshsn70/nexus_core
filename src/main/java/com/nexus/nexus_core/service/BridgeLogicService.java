package com.nexus.nexus_core.service;

import com.nexus.nexus_core.model.Transaction;
import org.springframework.stereotype.Service;

@Service
public class BridgeLogicService {

    public String processForMainframe(Transaction tx) {
        // 1. Logic: Risk Engine (Milestone 11-13)
        String risk = (tx.getAmount() != null && tx.getAmount() > 10000) ? "HIGH" : "LOW ";
        tx.setRiskLevel(risk.trim());

        // 2. Logic: Fixed-Width Padding (Milestone 8-10)
        // Format: PolicyID (15 chars) + Amount (10 chars) + Risk (5 chars)
        
        String paddedId = String.format("%-15s", tx.getPolicyId()); // Left aligned
        String paddedAmount = String.format("%010.0f", tx.getAmount()); // Zero padded
        String paddedRisk = String.format("%-5s", risk); // Right aligned

        return paddedId + paddedAmount + paddedRisk;
    }
}