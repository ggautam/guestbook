package com.guestbook.task.repository;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.guestbook.task.entity.InvitationEntity;

@Repository
@Transactional
public interface InvitationRepository extends JpaRepository<InvitationEntity, Serializable> {

	@Modifying
	@Query(value = "update gb_invitation set is_approved=:is_approved where invite_id=:invite_id", nativeQuery = true)
	public void updateInviteConfirmation(@Param("invite_id") long invite_id, @Param("is_approved") boolean is_approved);

	@Modifying
	@Query(value = "update gb_invitation set is_active=0 where invite_id=:invite_id", nativeQuery = true)
	public void updateDeactivateInvite(@Param("invite_id") long invite_id);
	
	InvitationEntity findByInviteId(long id);

	@Query(value = "select * from gb_invitation where invite_id=:invite_id and user_id=:user_id", nativeQuery = true)
	InvitationEntity getInvitationByInviteIdAndUserId(@Param("invite_id") long invite_id,
			@Param("user_id") long user_id);

	@Query(value = "select * from gb_invitation where user_id=:user_id and is_active = 1 order by invite_create_dt desc", nativeQuery = true)
	public List<InvitationEntity> getInvitationsByUserId(@Param("user_id") long user_id);

	@Query(value = "select i.invite_id, u.uid, u.name, u.email, u.gsm, i.event_image, i.event_message, i.is_approved, i.invite_create_dt from gb_invitation i inner join gb_user u ON i.user_id = u.uid where i.is_active = 1 group by i.invite_id order by i.invite_create_dt desc", nativeQuery = true)
	public List<Object[]> getUserInvitations();

	@Query(value = "select i.invite_id, u.uid, u.name, u.email, u.gsm, i.event_image, i.event_message, i.is_approved, i.invite_create_dt from gb_invitation i inner join gb_user u ON i.user_id = u.uid where u.uid=:user_id and i.is_active = 1 group by i.invite_id order by i.invite_create_dt desc", nativeQuery = true)
	public List<Object[]> getUserInvitationsByUserId(@Param("user_id") long user_id);

}
