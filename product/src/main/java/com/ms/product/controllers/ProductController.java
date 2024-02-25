package com.ms.product.controllers;

import com.ms.product.dto.ProductDTO;
import com.ms.product.services.interfaces.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final IProductService productService;
    private final JobLauncher jobLauncher;
    private final Job job;

    @PutMapping
    public ResponseEntity<ProductDTO> createOrUpdate(@RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.createOrUpdate(productDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> list() {
        return new ResponseEntity<>(productService.listAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> get(@PathVariable Long id) {
        return new ResponseEntity<>(productService.getById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return new ResponseEntity<>(productService.deleteById(id), HttpStatus.OK);
    }

    @PutMapping("/like")
    public ResponseEntity<ProductDTO> like (@PathVariable Long id, @PathVariable Boolean likeBool) {
        return new ResponseEntity<>(productService.likeById(id, likeBool != null ? likeBool : true), HttpStatus.OK);
    }

    @PutMapping("/dislike")
    public ResponseEntity<ProductDTO> dislike (@PathVariable Long id, @PathVariable Boolean dislikeBool) {
        return new ResponseEntity<>(productService.dislikeById(id, dislikeBool != null ? dislikeBool : true), HttpStatus.OK);
    }

    @GetMapping("/load-data")
    public BatchStatus load() throws Exception {
        Map<String, JobParameter<?>> parameters = new HashMap<>();
        parameters.put("time", new JobParameter<>(String.valueOf(System.currentTimeMillis()), String.class));
        JobExecution jobExecution = jobLauncher.run(job, new JobParameters(parameters));

        while(jobExecution.isRunning()) {
            System.out.println("...");
        }

        return jobExecution.getStatus();
    }
}
