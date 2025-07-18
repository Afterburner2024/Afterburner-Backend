package com.afterburner.comment.controller;

import com.afterburner.comment.model.dto.CommentDTO;
import com.afterburner.comment.service.CommentService;
import com.afterburner.common.codes.ErrorCode;
import com.afterburner.common.codes.SuccessCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/study-group/{studyGroupId}")
    public ResponseEntity<?> createStudyGroupComment(@PathVariable Integer studyGroupId,
                                                     @Valid @RequestBody CommentDTO dto) {
        CommentDTO result = commentService.createStudyGroupComment(studyGroupId, dto);
        return ResponseEntity.status(SuccessCode.INSERT.getStatus()).body(result);
    }

    @GetMapping("/study-group/{studyGroupId}")
    public List<CommentDTO> getStudyGroupComments(@PathVariable Integer studyGroupId) {
        return commentService.getStudyGroupComments(studyGroupId);
    }

    @PostMapping("/project/{projectId}")
    public ResponseEntity<?> createProjectComment(@PathVariable Integer projectId,
                                                  @Valid @RequestBody CommentDTO dto) {
        CommentDTO result = commentService.createProjectComment(projectId, dto);
        return ResponseEntity.status(SuccessCode.INSERT.getStatus()).body(result);
    }

    @GetMapping("/project/{projectId}")
    public List<CommentDTO> getProjectComments(@PathVariable Integer projectId) {
        return commentService.getProjectComments(projectId);
    }

    @PostMapping("/qna/{qnaId}")
    public ResponseEntity<?> createQnaComment(@PathVariable Integer qnaId,
                                              @Valid @RequestBody CommentDTO dto) {
        CommentDTO result = commentService.createQnaComment(qnaId, dto);
        return ResponseEntity.status(SuccessCode.INSERT.getStatus()).body(result);
    }

    @GetMapping("/qna/{qnaId}")
    public List<CommentDTO> getQnaComments(@PathVariable Integer qnaId) {
        return commentService.getQnaComments(qnaId);
    }

    @PostMapping("/community/{communityId}")
    public ResponseEntity<?> createCommunityComment(@PathVariable Integer communityId,
                                                    @Valid @RequestBody CommentDTO dto) {
        CommentDTO result = commentService.createCommunityComment(communityId, dto);
        return ResponseEntity.status(SuccessCode.INSERT.getStatus()).body(result);
    }

    @GetMapping("/community/{communityId}")
    public List<CommentDTO> getCommunityComments(@PathVariable Integer communityId) {
        return commentService.getCommunityComments(communityId);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Integer commentId,
                                           @RequestParam Integer userId,
                                           @RequestBody CommentDTO dto) {
        CommentDTO result = commentService.updateComment(commentId, userId, dto.getCommentContent());
        if (result == null) {
            return ResponseEntity.status(ErrorCode.NOT_FOUND_ERROR.getStatus()).body(ErrorCode.NOT_FOUND_ERROR.getMessage());
        }
        return ResponseEntity.status(SuccessCode.UPDATE.getStatus()).body(result);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer commentId,
                                           @RequestParam Integer userId,
                                           @RequestParam(defaultValue = "false") boolean isAdmin) {
        CommentDTO result = commentService.deleteComment(commentId, userId, isAdmin);
        if (result == null) {
            return ResponseEntity.status(ErrorCode.NOT_FOUND_ERROR.getStatus()).body(ErrorCode.NOT_FOUND_ERROR.getMessage());
        }
        return ResponseEntity.status(SuccessCode.DELETE.getStatus()).body(result);
    }
}
