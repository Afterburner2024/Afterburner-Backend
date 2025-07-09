package com.afterburner.qna.service;

import com.afterburner.qna.model.dto.QnaDTO;
import com.afterburner.qna.model.entity.QnaEntity;
import com.afterburner.qna.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QnaService {

    private final QnaRepository qnaRepository;

    @Transactional
    public Integer createQna(QnaDTO.Request request) {
        QnaEntity qnaEntity = request.toEntity();
        return qnaRepository.save(qnaEntity).getQnaId();
    }

    public QnaDTO.Response getQna(Integer qnaId) {
        QnaEntity qnaEntity = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new IllegalArgumentException("해당 QNA를 찾을 수 없습니다. id=" + qnaId));
        return QnaDTO.Response.fromEntity(qnaEntity);
    }

    public List<QnaDTO.Response> getAllQnas() {
        return qnaRepository.findAll().stream()
                .map(QnaDTO.Response::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateQna(Integer qnaId, QnaDTO.Request request) {
        QnaEntity qnaEntity = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new IllegalArgumentException("해당 QNA를 찾을 수 없습니다. id=" + qnaId));
        qnaEntity.update(request.getQnaTitle(), request.getQnaContent());
    }

    @Transactional
    public void deleteQna(Integer qnaId) {
        QnaEntity qnaEntity = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new IllegalArgumentException("해당 QNA를 찾을 수 없습니다. id=" + qnaId));
        qnaEntity.delete();
    }
}