package com.afterburner.question.service;

import com.afterburner.common.enums.PostStatus;
import com.afterburner.question.model.dto.QuestionDTO;
import com.afterburner.question.model.entity.QuestionEntity;
import com.afterburner.question.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

	private final QuestionRepository questionRepository;

	@Autowired
	public QuestionService(QuestionRepository questionRepository) {
		this.questionRepository = questionRepository;
	}

	@Transactional
	public QuestionDTO createPost(QuestionDTO questionDTO) {
		// 토큰가져와서 userId 꺼내오기
		// userId 가 없을경우 로그인 정보가 없습니다 exception처리

		QuestionEntity entity = new QuestionEntity.Builder()
				.questionTitle(questionDTO.getQuestionTitle())
				.questionContent(questionDTO.getQuestionContent())
				.questionStatus(questionDTO.getQuestionStatus())
				.questionUserId(questionDTO.getQuestionUserId())
				.questionImg(questionDTO.getQuestionImg())
				.build();

		QuestionEntity savedEntity = questionRepository.save(entity);

		if (savedEntity != null) {
			QuestionDTO savedDTO = new QuestionDTO();
			savedDTO.setQuestionId(savedEntity.getQuestionId());
			savedDTO.setQuestionTitle(savedEntity.getQuestionTitle());
			savedDTO.setQuestionContent(savedEntity.getQuestionContent());
			savedDTO.setQuestionCreatedAt(savedEntity.getQuestionCreatedAt());
			savedDTO.setQuestionUpdatedAt(savedEntity.getQuestionUpdatedAt());
			savedDTO.setQuestionStatus(savedEntity.getQuestionStatus());
			savedDTO.setQuestionUserId(savedEntity.getQuestionUserId());
			savedDTO.setQuestionImg(savedEntity.getQuestionImg());

			return savedDTO;
		} else {
			return null;
		}
	}

	@Transactional
	public QuestionDTO updatePost(QuestionDTO questionDTO, Integer id) {
		// 토큰가져와서 userId 꺼내오기
		// userId 가 없을경우 로그인 정보가 없습니다 exception처리
		Optional<QuestionEntity> studyGroupEntity = questionRepository.findById(id);

		// 위에서 꺼낸 userId랑 조회한 게시글의 userId가 같은지 확인 -> 동일인이어야 수정 가능함

		// 같으면
		QuestionEntity entity = studyGroupEntity.get();
		entity.setQuestionTitle(questionDTO.getQuestionTitle());
		entity.setQuestionContent(questionDTO.getQuestionContent());
		entity.setQuestionImg(questionDTO.getQuestionImg());

		QuestionEntity savedEntity = questionRepository.save(entity);

		if (savedEntity != null) {
			QuestionDTO savedDTO = new QuestionDTO();
			savedDTO.setQuestionId(savedEntity.getQuestionId());
			savedDTO.setQuestionTitle(savedEntity.getQuestionTitle());
			savedDTO.setQuestionContent(savedEntity.getQuestionContent());
			savedDTO.setQuestionCreatedAt(savedEntity.getQuestionCreatedAt());
			savedDTO.setQuestionUpdatedAt(savedEntity.getQuestionUpdatedAt());
			savedDTO.setQuestionStatus(savedEntity.getQuestionStatus());
			savedDTO.setQuestionUserId(savedEntity.getQuestionUserId());
			savedDTO.setQuestionImg(savedEntity.getQuestionImg());

			return savedDTO;
		} else {
			return null;
		}
	}

	@Transactional
	public QuestionDTO deletePost(Integer id) {
		// 토큰가져와서 userId 꺼내오기
		// userId 가 없을경우 로그인 정보가 없습니다 exception처리
		Optional<QuestionEntity> studyGroupEntity = questionRepository.findById(id);

		// 위에서 꺼낸 userId랑 조회한 게시글의 userId가 같은지 확인 -> 동일인이어야 삭제 가능함
		// 같으면

		if (studyGroupEntity.isPresent()) { // 게시글 존재하면
			QuestionEntity entity = studyGroupEntity.get();
			entity.setQuestionStatus(PostStatus.DELETED); // 삭제상태로 변경
			entity.setQuestionDeletedAt(LocalDateTime.now());
			QuestionEntity savedEntity = questionRepository.save(entity);

			QuestionDTO resultDTO = new QuestionDTO();
			resultDTO.setQuestionId(savedEntity.getQuestionId());
			resultDTO.setQuestionTitle(savedEntity.getQuestionTitle());
			resultDTO.setQuestionContent(savedEntity.getQuestionContent());
			resultDTO.setQuestionCreatedAt(savedEntity.getQuestionCreatedAt());
			resultDTO.setQuestionUpdatedAt(savedEntity.getQuestionUpdatedAt());
			resultDTO.setQuestionStatus(savedEntity.getQuestionStatus());
			resultDTO.setQuestionUserId(savedEntity.getQuestionUserId());
			resultDTO.setQuestionImg(savedEntity.getQuestionImg());

			return resultDTO;

		} else {
			return null;
		}
	}

	public List<QuestionDTO> allStudyGroupList() {
		List<QuestionEntity> entities = questionRepository.findAll();
		List<QuestionDTO> studyGroupList = new ArrayList<>();

		if (entities.size() > 0) {
			for (QuestionEntity entity : entities) {
				QuestionDTO dto = new QuestionDTO();
				dto.setQuestionId(entity.getQuestionId());
				dto.setQuestionTitle(entity.getQuestionTitle());
				dto.setQuestionContent(entity.getQuestionContent());
				dto.setQuestionCreatedAt(entity.getQuestionCreatedAt());
				dto.setQuestionUpdatedAt(entity.getQuestionUpdatedAt());
				dto.setQuestionStatus(entity.getQuestionStatus());
				dto.setQuestionUserId(entity.getQuestionUserId());
				dto.setQuestionImg(entity.getQuestionImg());
				studyGroupList.add(dto);
			}
			return studyGroupList;
		}
		return new ArrayList<>();
	}

	public QuestionDTO detailPost(Integer id) {
		Optional<QuestionEntity> findEntity = questionRepository.findById(id);
		QuestionDTO detailDTO = new QuestionDTO();

		if (findEntity.isPresent()) {
			detailDTO.setQuestionId(findEntity.get().getQuestionId());
			detailDTO.setQuestionTitle(findEntity.get().getQuestionTitle());
			detailDTO.setQuestionContent(findEntity.get().getQuestionContent());
			detailDTO.setQuestionCreatedAt(findEntity.get().getQuestionCreatedAt());
			detailDTO.setQuestionUpdatedAt(findEntity.get().getQuestionUpdatedAt());
			detailDTO.setQuestionStatus(findEntity.get().getQuestionStatus());
			detailDTO.setQuestionUserId(findEntity.get().getQuestionUserId());
			detailDTO.setQuestionImg(findEntity.get().getQuestionImg());
		}
		return detailDTO;
	}
}
