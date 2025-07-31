package com.afterburner.qna.service;

import com.afterburner.common.enums.PostStatus;
import com.afterburner.qna.model.dto.QnaDTO;
import com.afterburner.qna.model.entity.QnaEntity;
import com.afterburner.qna.repository.QnaRepository;
import com.afterburner.global.exception.QnaNotFoundException;
import com.afterburner.common.codes.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class QnaService {

    private final QnaRepository qnaRepository;

    @Autowired
    public QnaService(QnaRepository qnaRepository) {
        this.qnaRepository = qnaRepository;
    }

    @Transactional
    public QnaDTO createQna(QnaDTO qnaDTO) {
        QnaEntity qna = new QnaEntity();
        qna.setQnaTitle(qnaDTO.getQnaTitle());
        qna.setQnaContent(qnaDTO.getQnaContent());
        qna.setQnaStatus(PostStatus.DEFAULT);
        qna.setQnaCreatedAt(LocalDateTime.now());
        qna.setQnaUpdatedAt(LocalDateTime.now());
        qna.setQnaDeletedAt(null);
        qna.setQnaUserId(qnaDTO.getQnaUserId());

        QnaEntity savedQna = qnaRepository.save(qna);

        return QnaDTO.builder()
                .qnaId(savedQna.getQnaId())
                .qnaTitle(savedQna.getQnaTitle())
                .qnaContent(savedQna.getQnaContent())
                .qnaAnswer(savedQna.getQnaAnswer())
                .qnaStatus(savedQna.getQnaStatus())
                .qnaUserId(savedQna.getQnaUserId())
                .qnaCreatedAt(savedQna.getQnaCreatedAt())
                .qnaUpdatedAt(savedQna.getQnaUpdatedAt())
                .qnaDeletedAt(savedQna.getQnaDeletedAt())
                .build();
    }

    public List<QnaDTO> getAllQnas() {
        List<QnaEntity> qnas = qnaRepository.findAll()
                .stream()
                .filter(qna -> qna.getQnaStatus() == PostStatus.DEFAULT)
                .collect(Collectors.toList());

        return qnas.stream()
                .map(qna -> QnaDTO.builder()
                        .qnaId(qna.getQnaId())
                        .qnaTitle(qna.getQnaTitle())
                        .qnaContent(qna.getQnaContent())
                        .qnaAnswer(qna.getQnaAnswer())
                        .qnaStatus(qna.getQnaStatus())
                        .qnaUserId(qna.getQnaUserId())
                        .qnaCreatedAt(qna.getQnaCreatedAt())
                        .qnaUpdatedAt(qna.getQnaUpdatedAt())
                        .qnaDeletedAt(qna.getQnaDeletedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public QnaDTO getQnaById(Integer qnaId) {
        QnaEntity qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new QnaNotFoundException(ErrorCode.NOT_FOUND_ERROR, "QnA를 찾을 수 없습니다."));

        if (qna.getQnaStatus() != PostStatus.DEFAULT) {
            throw new QnaNotFoundException(ErrorCode.NOT_FOUND_ERROR, "이미 삭제된 QNA입니다..");
        }

        return QnaDTO.builder()
                .qnaId(qna.getQnaId())
                .qnaTitle(qna.getQnaTitle())
                .qnaContent(qna.getQnaContent())
                .qnaAnswer(qna.getQnaAnswer())
                .qnaStatus(qna.getQnaStatus())
                .qnaUserId(qna.getQnaUserId())
                .qnaCreatedAt(qna.getQnaCreatedAt())
                .qnaUpdatedAt(qna.getQnaUpdatedAt())
                .qnaDeletedAt(qna.getQnaDeletedAt())
                .build();
    }

    @Transactional
    public QnaDTO updateQna(Integer qnaId, QnaDTO qnaDTO) {
        QnaEntity qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new QnaNotFoundException(ErrorCode.NOT_FOUND_ERROR, "해당 QnA를 찾을 수 없습니다."));

        // 로그인 코드 완성되면
        // if (!qna.getUserId().equals(getCurrentUserId())) {
        //     throw new RuntimeException("작성자 본인만 수정이 가능합니다.");
        // }

        qna.setQnaTitle(qnaDTO.getQnaTitle());
        qna.setQnaContent(qnaDTO.getQnaContent());
        qna.setQnaUpdatedAt(LocalDateTime.now());
        qna.setQnaStatus(PostStatus.DEFAULT);
        qna.setQnaUserId(qnaDTO.getQnaUserId());

        QnaEntity updatedQna = qnaRepository.save(qna);

        return QnaDTO.builder()
                .qnaId(updatedQna.getQnaId())
                .qnaTitle(updatedQna.getQnaTitle())
                .qnaContent(updatedQna.getQnaContent())
                .qnaAnswer(updatedQna.getQnaAnswer())
                .qnaStatus(updatedQna.getQnaStatus())
                .qnaUserId(updatedQna.getQnaUserId())
                .qnaCreatedAt(updatedQna.getQnaCreatedAt())
                .qnaUpdatedAt(updatedQna.getQnaUpdatedAt())
                .qnaDeletedAt(updatedQna.getQnaDeletedAt())
                .build();
    }

    @Transactional
    public QnaDTO deleteQna(Integer qnaId) {
        QnaEntity qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new QnaNotFoundException(ErrorCode.NOT_FOUND_ERROR, "QnA를 찾을 수 없습니다."));

        // if (!qna.getUserId().equals(getCurrentUserId()) && !isCurrentUserAdmin()) {
        //     throw new RuntimeException("You are not authorized to delete this QnA.");
        // }

        qna.setQnaStatus(PostStatus.DELETED);
        qna.setQnaDeletedAt(LocalDateTime.now());

        QnaEntity deletedQna = qnaRepository.save(qna);

        return QnaDTO.builder()
                .qnaId(deletedQna.getQnaId())
                .qnaTitle(deletedQna.getQnaTitle())
                .qnaContent(deletedQna.getQnaContent())
                .qnaAnswer(deletedQna.getQnaAnswer())
                .qnaStatus(deletedQna.getQnaStatus())
                .qnaUserId(deletedQna.getQnaUserId())
                .qnaCreatedAt(deletedQna.getQnaCreatedAt())
                .qnaUpdatedAt(deletedQna.getQnaUpdatedAt())
                .qnaDeletedAt(deletedQna.getQnaDeletedAt())
                .build();
    }

    @Transactional
    public QnaDTO addAnswer(Integer qnaId, QnaDTO qnaDTO) {
        QnaEntity qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new QnaNotFoundException(ErrorCode.NOT_FOUND_ERROR, "해당 QnA를 찾을 수 없습니다."));

        qna.setQnaAnswer(qnaDTO.getQnaAnswer());
        qna.setQnaUpdatedAt(LocalDateTime.now());

        QnaEntity answeredQna = qnaRepository.save(qna);

        return QnaDTO.builder()
                .qnaId(answeredQna.getQnaId())
                .qnaTitle(answeredQna.getQnaTitle())
                .qnaContent(answeredQna.getQnaContent())
                .qnaAnswer(answeredQna.getQnaAnswer())
                .qnaStatus(answeredQna.getQnaStatus())
                .qnaUserId(answeredQna.getQnaUserId())
                .qnaCreatedAt(answeredQna.getQnaCreatedAt())
                .qnaUpdatedAt(answeredQna.getQnaUpdatedAt())
                .qnaDeletedAt(answeredQna.getQnaDeletedAt())
                .build();
    }
}
