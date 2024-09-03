package com.vou.statistics.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.vou.statistics.model.Transaction;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vou.statistics.context.TransactionContext;
import com.vou.statistics.creator.TransactionFactoryCreator;
import com.vou.statistics.dto.TransactionDto;
import com.vou.statistics.dto.VoucherItemsConversionInfo;
import com.vou.statistics.entity.ItemSharedTransaction;
import com.vou.statistics.entity.VoucherConversionTransaction;
import com.vou.statistics.entity.VoucherUsedTransaction;
import com.vou.statistics.factory.TransactionFactory;
import com.vou.statistics.mapper.TransactionMapper;
// import com.vou.statistics.mapper.TransactionMapper;
import com.vou.statistics.service.TransactionService;
import com.vou.statistics.strategy.ItemSharedTransactionStrategy;
import com.vou.statistics.strategy.TransactionStrategy;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    
    private TransactionService<ItemSharedTransaction>           itemSharedTransactionService;
    private TransactionService<VoucherUsedTransaction>          voucherUsedTransactionService;
    private TransactionService<VoucherConversionTransaction>    voucherConversionTransactionService;

    @Autowired
    public TransactionController(TransactionService<ItemSharedTransaction> itemSharedTransactionService,
                                 TransactionService<VoucherUsedTransaction> voucherUsedTransactionService,
                                 TransactionService<VoucherConversionTransaction> voucherConversionTransactionService) {
        this.itemSharedTransactionService = itemSharedTransactionService;
        this.voucherUsedTransactionService = voucherUsedTransactionService;
        this.voucherConversionTransactionService = voucherConversionTransactionService;
    }

    @GetMapping("/item_shared")
    public ResponseEntity<List<ItemSharedTransaction>> getAllItemSharedTransactions() {
        return ResponseEntity.ok(itemSharedTransactionService.getAllTransactions());
    }

    @GetMapping("/voucher_used")
    public ResponseEntity<List<VoucherUsedTransaction>> getAllVoucherUsedTransactions() {
        return ResponseEntity.ok(voucherUsedTransactionService.getAllTransactions());
    }

    @GetMapping("/voucher_conversion")
    public ResponseEntity<List<VoucherConversionTransaction>> getAllVoucherConversionTransactions() {
        return ResponseEntity.ok(voucherConversionTransactionService.getAllTransactions());
    }

    @GetMapping("/item_shared/{id}")
    public ResponseEntity<ItemSharedTransaction> getTransactionById(@PathVariable String id) {
        if (id.length() != 24) {
            return ResponseEntity.badRequest().body(null); // Or return a custom error message
        }

        return ResponseEntity.ok(itemSharedTransactionService.getTransactionById(new ObjectId(id)));
    }

    @GetMapping("/voucher_used/{id}")
    public ResponseEntity<VoucherUsedTransaction> getVoucherUsedTransactionById(@PathVariable String id) {
        if (id.length() != 24) {
            return ResponseEntity.badRequest().body(null); // Or return a custom error message
        }

        return ResponseEntity.ok(voucherUsedTransactionService.getTransactionById(new ObjectId(id)));
    }

    @GetMapping("/voucher_conversion/{id}")
    public ResponseEntity<VoucherConversionTransaction> getVoucherConversionTransactionById(@PathVariable String id) {
        if (id.length() != 24) {
            return ResponseEntity.badRequest().body(null); // Or return a custom error message
        }

        return ResponseEntity.ok(voucherConversionTransactionService.getTransactionById(new ObjectId(id)));
    }

    @GetMapping("/item_shared/sender/{senderId}")
    public ResponseEntity<List<ItemSharedTransaction>> getTransactionsBySender(@PathVariable String senderId) {
        return ResponseEntity.ok(itemSharedTransactionService.getTransactionsByPlayer(senderId));
    }

    @GetMapping("/voucher_used/sender/{senderId}")
    public ResponseEntity<List<VoucherUsedTransaction>> getVoucherUsedTransactionsBySender(@PathVariable String senderId) {
        return ResponseEntity.ok(voucherUsedTransactionService.getTransactionsByPlayer(senderId));
    }

    @GetMapping("/voucher_conversion/sender/{senderId}")
    public ResponseEntity<List<VoucherConversionTransaction>> getVoucherConversionTransactionsBySender(@PathVariable String senderId) {
        return ResponseEntity.ok(voucherConversionTransactionService.getTransactionsByPlayer(senderId));
    }

    @GetMapping("/item_shared/recipient/{recipientId}")
    public ResponseEntity<List<ItemSharedTransaction>> getTransactionsByRecipient(@PathVariable String recipientId) {
        return ResponseEntity.ok(itemSharedTransactionService.getTransactionsByRecipient(recipientId));
    }

    @GetMapping("/voucher_used/recipient/{recipientId}")
    public ResponseEntity<List<VoucherUsedTransaction>> getVoucherUsedTransactionsByRecipient(@PathVariable String recipientId) {
        return ResponseEntity.ok(voucherUsedTransactionService.getTransactionsByRecipient(recipientId));
    }

    @GetMapping("/voucher_conversion/recipient/{recipientId}")
    public ResponseEntity<List<VoucherConversionTransaction>> getVoucherConversionTransactionsByRecipient(@PathVariable String recipientId) {
        return ResponseEntity.ok(voucherConversionTransactionService.getTransactionsByRecipient(recipientId));
    }

    @GetMapping("/item_shared/sender/{senderId}/recipient/{recipientId}")
    public ResponseEntity<List<ItemSharedTransaction>> getTransactionsBySenderAndRecipient(@PathVariable String senderId, @PathVariable String recipientId) {
        return ResponseEntity.ok(itemSharedTransactionService.getTransactionsBySenderAndRecipient(senderId, recipientId));
    }

    @GetMapping("/voucher_used/sender/{senderId}/recipient/{recipientId}")
    public ResponseEntity<List<VoucherUsedTransaction>> getVoucherUsedTransactionsBySenderAndRecipient(@PathVariable String senderId, @PathVariable String recipientId) {
        return ResponseEntity.ok(voucherUsedTransactionService.getTransactionsBySenderAndRecipient(senderId, recipientId));
    }

    @GetMapping("/voucher_conversion/sender/{senderId}/recipient/{recipientId}")
    public ResponseEntity<List<VoucherConversionTransaction>> getVoucherConversionTransactionsBySenderAndRecipient(@PathVariable String senderId, @PathVariable String recipientId) {
        return ResponseEntity.ok(voucherConversionTransactionService.getTransactionsBySenderAndRecipient(senderId, recipientId));
    }

    @GetMapping("/item_shared/artifact/{artifactId}")
    public ResponseEntity<List<ItemSharedTransaction>> getTransactionsByArtifact(@PathVariable String artifactId) {
        return ResponseEntity.ok(itemSharedTransactionService.getTransactionsByArtifact(artifactId));
    }

    @GetMapping("/voucher_used/artifact/{artifactId}")
    public ResponseEntity<List<VoucherUsedTransaction>> getVoucherUsedTransactionsByArtifact(@PathVariable String artifactId) {
        return ResponseEntity.ok(voucherUsedTransactionService.getTransactionsByArtifact(artifactId));
    }

    @GetMapping("/voucher_conversion/artifact/{artifactId}")
    public ResponseEntity<List<VoucherConversionTransaction>> getVoucherConversionTransactionsByArtifact(@PathVariable String artifactId) {
        return ResponseEntity.ok(voucherConversionTransactionService.getTransactionsByArtifact(artifactId));
    }
    
    // usually one happens at a time
    @PostMapping("/process")
    public ResponseEntity<Boolean> processTransactions(@RequestBody List<TransactionDto> transactionDtos) {
        System.out.println("Processing transactions...");
        List<Transaction> transactions = transactionDtos.stream()
            .map(TransactionMapper::toEntity)
            .collect(Collectors.toList());

        System.out.println("Transactions: " + transactions);

        for (Transaction _transaction : transactions) {
            TransactionContext transactionContext = new TransactionContext();

            TransactionFactory transactionFactory = TransactionFactoryCreator.getTransactionFactory(_transaction.getTransactionType());
            if (transactionFactory == null) {
                // Handle the case where no factory is found
                return ResponseEntity.ok(false);
            }

            // _transaction = transactionFactory.createTransaction(); // not sure
            // transactionContext.setTransactionStrategy(TransactionFactoryCreator.getTransactionStrategy(_transaction.getTransactionType()));
            // if (transactionContext.executeStrategy(_transaction) == false) {
            //     return ResponseEntity.ok(false);
            // }
            System.out.println("Transaction type: " + _transaction.getTransactionType());
            // // Create the transaction based on the factory
            Transaction createdTransaction = transactionFactory.createTransaction(TransactionMapper.toDto(_transaction));  // Pass original transaction or additional data if needed
            System.out.println("Created transaction: " + createdTransaction);
            // // Create the transaction based on the factory
            TransactionStrategy transactionStrategy = TransactionFactoryCreator.getTransactionStrategy(_transaction.getTransactionType());
            transactionContext.setTransactionStrategy(transactionStrategy);

            if (transactionContext.executeStrategy(createdTransaction) == false) {
                return ResponseEntity.ok(false);
            }
        }

        return ResponseEntity.ok(true);
    }

    // @PostMapping("/vouchers/process")
    // public ResponseEntity<Boolean> processVoucherTransaction(@RequestBody VoucherItemsConversionInfo voucherItemsConversionInfo) {
    //     return ResponseEntity.ok(transactionService.processVoucherTransaction(voucherItemsConversionInfo));
    // }
}
