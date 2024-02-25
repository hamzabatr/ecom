package com.ms.product.controllers;

import com.ms.product.dto.CommentDTO;
import com.ms.product.dto.body.CommentBody;
import com.ms.product.services.interfaces.ICommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final ICommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDTO> postCommentToProduct(@RequestBody CommentBody commentBody) {
        return new ResponseEntity<>(commentService.postCommentToProduct(commentBody), HttpStatus.CREATED);
    }

    @PutMapping("/like")
    public ResponseEntity<CommentDTO> like (@RequestParam(name = "id") Long id, @RequestParam(name = "likeBool") Boolean likeBool) {
        return new ResponseEntity<>(commentService.likeById(id, likeBool != null ? likeBool : true), HttpStatus.OK);
    }

    @PutMapping("/dislike")
    public ResponseEntity<CommentDTO> dislike (@RequestParam(name = "id") Long id, @RequestParam(name = "dislikeBool") Boolean dislikeBool) {
        return new ResponseEntity<>(commentService.dislikeById(id, dislikeBool != null ? dislikeBool : true), HttpStatus.OK);
    }
}
