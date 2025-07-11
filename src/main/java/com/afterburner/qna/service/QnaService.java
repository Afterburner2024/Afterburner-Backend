package com.afterburner.qna.service;

import com.afterburner.common.enums.PostStatus;
import com.afterburner.qna.model.dto.QnaDTO;
import com.afterburner.qna.model.entity.QnaEntity;
import com.afterburner.qna.repository.QnaRepository;
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
        // qna.setUserId(getCurrentUserId());

        QnaEntity savedQna = qnaRepository.save(qna);

        return new QnaDTO(
                savedQna.getQnaId(),
                savedQna.getQnaTitle(),
                savedQna.getQnaContent(),
                savedQna.getQnaStatus(),
                savedQna.getQnaCreatedAt(),
                savedQna.getQnaUpdatedAt(),
                savedQna.getQnaDeletedAt()
        );
    }

    public List<QnaDTO> getAllQnas() {
        List<QnaEntity> qnas = qnaRepository.findAll()
                .stream()
                .filter(qna -> qna.getQnaStatus() == PostStatus.DEFAULT)
                .collect(Collectors.toList());

        return qnas.stream()
                .map(qna -> new QnaDTO(
                        qna.getQnaId(),
                        qna.getQnaTitle(),
                        qna.getQnaContent(),
                        qna.getQnaStatus(),
                        qna.getQnaCreatedAt(),
                        qna.getQnaUpdatedAt(),
                        qna.getQnaDeletedAt()
                ))
                .collect(Collectors.toList());
    }

    public QnaDTO getQnaById(Integer qnaId) {
        QnaEntity qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new RuntimeException("QnA를 찾을 수 없습니다."));

        if (qna.getQnaStatus() != PostStatus.DEFAULT) {
            throw new RuntimeException("이미 삭제된 QNA입니다..");
        }

        return new QnaDTO(
                qna.getQnaId(),
                qna.getQnaTitle(),
                qna.getQnaContent(),
                qna.getQnaStatus(),
                qna.getQnaCreatedAt(),
                qna.getQnaUpdatedAt(),
                qna.getQnaDeletedAt()
        );
    }

    @Transactional
    public QnaDTO updateQna(Integer qnaId, QnaDTO qnaDTO) {
        QnaEntity qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new RuntimeException("해당 QnA를 찾을 수 없습니다."));

        // 로그인 코드 완성되면
        // if (!qna.getUserId().equals(getCurrentUserId())) {
        //     throw new RuntimeException("작성자 본인만 수정이 가능합니다.");
        // }

        qna.setQnaTitle(qnaDTO.getQnaTitle());
        qna.setQnaContent(qnaDTO.getQnaContent());
        qna.setQnaUpdatedAt(LocalDateTime.now());

        QnaEntity updatedQna = qnaRepository.save(qna);

        return new QnaDTO(
                updatedQna.getQnaId(),
                updatedQna.getQnaTitle(),
                updatedQna.getQnaContent(),
                updatedQna.getQnaStatus(),
                updatedQna.getQnaCreatedAt(),
                updatedQna.getQnaUpdatedAt(),
                updatedQna.getQnaDeletedAt()
        );
    }

    @Transactional
    public QnaDTO deleteQna(Integer qnaId) {
        QnaEntity qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new RuntimeException("QnA를 찾을 수 없습니다."));

        // if (!qna.getUserId().equals(getCurrentUserId()) && !isCurrentUserAdmin()) {
        //     throw new RuntimeException("You are not authorized to delete this QnA.");
        // }

        qna.setQnaStatus(PostStatus.DELETED);
        qna.setQnaDeletedAt(LocalDateTime.now());

        QnaEntity deletedQna = qnaRepository.save(qna);

        return new QnaDTO(
                deletedQna.getQnaId(),
                deletedQna.getQnaTitle(),
                deletedQna.getQnaContent(),
                deletedQna.getQnaStatus(),
                deletedQna.getQnaCreatedAt(),
                deletedQna.getQnaUpdatedAt(),
                deletedQna.getQnaDeletedAt()
        );
    }
}
