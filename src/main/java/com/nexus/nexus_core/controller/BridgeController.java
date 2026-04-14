package com.nexus.nexus_core.controller;

import com.nexus.nexus_core.model.Transaction;
import com.nexus.nexus_core.repository.TransactionRepository;
import com.nexus.nexus_core.service.BridgeLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bridge")
@CrossOrigin(origins = "*") // Allows your Frontend to talk to the Backend
public class BridgeController {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private BridgeLogicService bridgeService;

    /**
     * Main Endpoint: Receives JSON, Processes Risk, Saves to DB, 
     * and returns Mainframe-ready Fixed-Width String.
     */
    @PostMapping("/process")
    public String processData(@RequestBody Transaction request) {
        try {
            // 1. Validation
            if (request == null || request.getPolicyId() == null) {
                return "TRANS_ERROR: INVALID_INPUT";
            }

            // 2. Execute Transformation & Risk Engine
            // This calculates High/Low risk and generates the 30-character string
            String mainframeData = bridgeService.processForMainframe(request);
            
            // 3. Update Audit Status
            request.setStatus("PROCESSED");

            // 4. Save to MySQL Audit Vault
            repository.save(request);

            // 5. Return the "Mainframe Stream" to the caller
            // Format: TRANS_BEGIN + PaddedString + TRANS_END
            return "TRANS_BEGIN\n" + mainframeData + "\nTRANS_END";

        } catch (Exception e) {
            e.printStackTrace();
            return "TRANS_ERROR: " + e.getMessage();
        }
    }

    /**
     * Health Check Endpoint
     */
    @GetMapping("/test")
    public String test() {
        return "NEXUS-CORE SYSTEM STATUS: ONLINE";
    }
}