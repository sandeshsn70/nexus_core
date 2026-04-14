package com.nexus.nexus_core.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "audit_vault")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "policy_id") 
    private String policyId;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "risk_level")
    private String riskLevel;

    @Column(name = "status")
    private String status;

    // Explicit Getters/Setters to ensure Spring can always see the data
    public String getPolicyId() { return policyId; }
    public void setPolicyId(String policyId) { this.policyId = policyId; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }
    public void setStatus(String status) { this.status = status; }
}