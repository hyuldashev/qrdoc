package com.example.qrdoc.controller;

import com.example.qrdoc.model.Document;
import com.example.qrdoc.model.AttemptTracker;
import com.example.qrdoc.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class VerifyController {

    @Autowired
    private DocumentRepository documentRepository;

    @GetMapping("/verify/{id}")
    public String verifyDocument(@PathVariable String id, Model model) {
        Document doc = documentRepository.findById(id).orElse(null);

        if (doc == null) {
            model.addAttribute("message", "Document soxta yoki topilmadi.");
            return "fake";
        }

        if (AttemptTracker.isBlocked(id)) {
            model.addAttribute("message", "Juda ko'p urinish. Iltimos 5 daqiqadan so'ng harakat qilib ko'ring.");
            return "blocked";
        }

        model.addAttribute("id", id);
        return "enter_code";
    }

    @PostMapping("/verify/{id}")
    public String checkCode(@PathVariable String id, @RequestParam String code, Model model) {
        Document doc = documentRepository.findById(id).orElse(null);
        if (doc == null) {
            model.addAttribute("message", "Document soxta.");
            return "fake";
        }

        if (AttemptTracker.isBlocked(id)) {
            model.addAttribute("message", "Juda ko'p urinish. Iltimos 5 daqiqadan so'ng harakat qilib ko'ring.");
            return "blocked";
        }

        if (doc.getSecretCode().equals(code)) {
            AttemptTracker.recordAttempt(id, true);
            model.addAttribute("doc", doc);
            model.addAttribute("valid", doc.getExpiryDate().isAfter(java.time.LocalDate.now()));
            return "details";
        } else {
            AttemptTracker.recordAttempt(id, false);
            model.addAttribute("id", id);
            model.addAttribute("error", "Kiritilgan kod noto'g'ri.");
            return "enter_code";
        }
    }
}
