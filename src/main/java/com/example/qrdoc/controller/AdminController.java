package com.example.qrdoc.controller;

import com.example.qrdoc.model.Document;
import com.example.qrdoc.repository.DocumentRepository;
import com.example.qrdoc.service.QrService;
import com.google.zxing.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private QrService qrService;

    @GetMapping
    public String adminPage() {
        return "admin";
    }

    @PostMapping("/add")
    public String addDocument(
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String expiryDate,
            @RequestParam String issuedBy,
            @RequestParam String details,
            @RequestParam String secretCode,
            @RequestParam("qrFile") MultipartFile qrFile,
            Model model
    ) throws IOException, NotFoundException {

        // Расшифровываем QR и получаем из него ссылку
        String qrValue = qrService.decodeQr(qrFile);

        // Извлекаем ID из ссылки вида http://localhost:8080/verify/{id}
        String extractedId = extractIdFromQrLink(qrValue);

        if (extractedId == null || extractedId.isEmpty()) {
            model.addAttribute("message", "Не удалось извлечь ID из QR-ссылки.");
            return "admin";
        }

        Document doc = new Document();
        doc.setId(extractedId);
        doc.setName(name);
        doc.setSurname(surname);
        doc.setExpiryDate(LocalDate.parse(expiryDate));
        doc.setIssuedBy(issuedBy);
        doc.setDetails(details);
        doc.setQrCodeValue(qrValue);
        doc.setSecretCode(secretCode);

        documentRepository.save(doc);

        model.addAttribute("message", "Document muvaffaqiyatli qo'shildi!");
        return "admin";
    }

    // Вспомогательный метод для извлечения ID из QR-ссылки
    private String extractIdFromQrLink(String qrValue) {
        if (qrValue != null && qrValue.contains("/verify/")) {
            return qrValue.substring(qrValue.lastIndexOf("/verify/") + 8);
        }
        return null;
    }

    @GetMapping("/list")
    public String listDocuments(Model model) {
        model.addAttribute("documents", documentRepository.findAll());
        return "list";
    }
}