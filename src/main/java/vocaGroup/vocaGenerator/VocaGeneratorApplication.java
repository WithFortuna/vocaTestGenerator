package vocaGroup.vocaGenerator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import vocaGroup.vocaGenerator.domain.*;

import java.util.List;

@SpringBootApplication
public class VocaGeneratorApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ac = SpringApplication.run(VocaGeneratorApplication.class, args);
	/*	EntityManagerFactory emf = ac.getBean(EntityManagerFactory.class);
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();


		Voca voca1 = new Voca("a", "ㅁ");
		Voca voca2 = new Voca("as", "ㅁ");
		em.persist(voca1);
		em.persist(voca2);
		Handout handout = new Handout("32");
		em.persist(handout);
		VocaHandout vocaHandout1 = new VocaHandout(voca1, handout);
		VocaHandout vocaHandout2 = new VocaHandout(voca2, handout);
		em.persist(vocaHandout2);
		em.persist(vocaHandout1);
		handout.addVoca(vocaHandout1);
		handout.addVoca(vocaHandout2);

		Test test = new Test("23", handout);
		List<VocaBlock> vocaBlocks = test.getTestVocabs();
		VocaBlock vocaBlock1 = new VocaBlock();
		vocaBlock1.setEnglish("a");
		vocaBlock1.setKorean("ㅁ");
		VocaBlock vocaBlock2 = new VocaBlock();
		vocaBlock2.setEnglish("asadf");
		vocaBlock2.setKorean("ㅁㄴㅇㄻㄴㄹㅇ");
		vocaBlocks.add(vocaBlock2);
		vocaBlocks.add(vocaBlock1);

		em.persist(test);

		transaction.commit();
		em.close();
*/
	}

}
