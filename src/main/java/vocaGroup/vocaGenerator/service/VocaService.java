package vocaGroup.vocaGenerator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vocaGroup.vocaGenerator.domain.Handout;
import vocaGroup.vocaGenerator.domain.User;
import vocaGroup.vocaGenerator.domain.Voca;
import vocaGroup.vocaGenerator.domain.VocaHandout;
import vocaGroup.vocaGenerator.login.utility.SecurityUtil;
import vocaGroup.vocaGenerator.repository.HandoutRepository;
import vocaGroup.vocaGenerator.repository.VocaRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VocaService {
    private final VocaRepository vocaRepository;
    private final HandoutRepository handoutRepository;

    //등록 조회 삭제
    @Transactional
    public void join(Voca voca) {
        vocaRepository.save(voca);
    }

    public Voca findById(Long id) {
        return vocaRepository.findById(id);
    }

    public List<Voca> findAll(Long userId) {
        return vocaRepository.findAll(userId);
    }

    @Transactional
    public void delete(Long id) {
        vocaRepository.delete(id);
    }

    /*VocaHandout 생성
    입력: List<Long id> , 반환: List<VocaHandout>
*/
    @Transactional
    public List<VocaHandout> createVocaHandout(List<Long> idList, Handout handout) {
        List<VocaHandout> vocaHandoutList = new ArrayList<>();
        User currentUser = SecurityUtil.getCurrentUser();

        for (Long id : idList) {
            Voca findVoca = vocaRepository.findById(id);
            VocaHandout vocaHandout = new VocaHandout(findVoca, handout,currentUser);
            vocaRepository.saveVocaHandout(vocaHandout);
            vocaHandoutList.add(vocaHandout);
        }

        return vocaHandoutList;
    }
}
