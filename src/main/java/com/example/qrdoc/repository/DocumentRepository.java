package com.example.qrdoc.repository;

import com.example.qrdoc.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, String> {
}
