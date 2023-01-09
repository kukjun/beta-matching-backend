package io.wisoft.testermatchingplatform.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.wisoft.testermatchingplatform.domain.Mission;
import io.wisoft.testermatchingplatform.domain.QMission;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static io.wisoft.testermatchingplatform.domain.QMission.*;

@Repository
@RequiredArgsConstructor
public class MissionRepositoryImpl implements MissionCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;

    @Override
    public List<Mission> findDeadLineTop4Mission(LocalDate currentDate) {
        String jpql = "select m " +
                "from Mission m " +
                "where m.missionDate.recruitmentTimeStart <= :currentDate and m.missionDate.recruitmentTimeEnd >= :currentDate " +
                "order by m.missionDate.recruitmentTimeEnd-:currentDate ";
        return entityManager.createQuery(jpql, Mission.class)
                .setParameter("currentDate", currentDate)
                .setFirstResult(0)
                .setMaxResults(4)
                .getResultList();
    }

    @Override
    public List<Mission> findPopularTop4Mission(LocalDate currentDate) {
        return jpaQueryFactory.selectFrom(mission)
                .where(mission.missionDate.recruitmentTimeStart.after(currentDate).not())
                .where(mission.missionDate.recruitmentTimeEnd.before(currentDate).not())
                .orderBy(mission.applyInformationList.size().desc())
                .offset(0)
                .limit(4)
                .fetch();
    }
    //    public List<Mission> findDeadLineTop4Mission() {
//        LocalDate currentDate = LocalDate.now();
//        String jpql = "select m " +
//                "from Mission m " +
//                "where m.missionDate.recruitmentTimeStart >= :currentDate " +
//                "and m.missionDate.recruitmentTimeEnd<= :currentDate";
//        List<Mission> missionList = em.createQuery(jpql, Mission.class)
//                .setParameter("currentDate", currentDate)
//                .getResultList();
//        missionList.sort(Comparator.comparingLong(Mission::remainApplyDays));
//        List<Mission> sortedList = new ArrayList<>();
//        if (missionList.size() >= 4) {
//            for (int i = 0; i < 4; i++) {
//                sortedList.add(missionList.get(i));
//            }
//        } else {
//            sortedList.addAll(missionList);
//        }
//        return sortedList;
//
//    }
//
//    public void findPopularTop4Mission() {
//        LocalDate currentDate = LocalDate.now();
//        String jpql = "select m " +
//                "from Mission m " +
//                "where m.missionDate.recruitmentTimeStart >= :currentDate " +
//                "and m.missionDate.recruitmentTimeEnd<= :currentDate " +
//                "order by m.applyInformationList.size";
//        em.createQuery(jpql, Mission.class)
//                .setParameter("currentDate", currentDate)
//                .setFirstResult(0)
//                .setMaxResults(4)
//                .getResultList();
//    }


}
