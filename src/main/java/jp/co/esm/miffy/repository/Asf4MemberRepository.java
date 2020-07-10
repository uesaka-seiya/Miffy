package jp.co.esm.miffy.repository;


import jp.co.esm.miffy.entity.Asf4Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Asf4MemberRepository extends JpaRepository<Asf4Member, Integer> {

    /**
     * 指定のフロアに一致かつ、Skip==falseかつ、指定のidより大きいidを持つメンバー情報をid昇順に並べた一番上のメンバー情報を取得する
     *
     * @param floor Stringのフロア情報
     * @param id int型のid
     * @return 条件に一致するメンバー情報をOptional型で返す
     */
    Optional<Asf4Member> findTopByFloorAndSkipFalseAndIdGreaterThanOrderByIdAsc(String floor, int id);

    /**
     * 指定のフロアに一致かつ、Skip==falseのメンバー情報をid昇順に並べた一番上のメンバー情報を取得する
     *
     * @param floor Stringのフロア情報
     * @return 条件に一致するメンバー情報をOptional型で返す
     */
    Optional<Asf4Member> findTopByFloorAndSkipFalseOrderByIdAsc(String floor);

    /**
     * IsCleaner==trueのメンバー情報を取得する
     *
     * @return 条件に一致するメンバー情報をOptional型で返す
     */
    Optional<Asf4Member> findByIsCleanerTrue();
}
