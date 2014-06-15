package com.billjc.essay.appointment.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.billjc.essay.appointment.entity.EssayAppointment;
import com.billjc.essay.appointment.entity.mapper.EssayAppointmentRowMapper;
import com.billjc.framework.BaseJdbcDaoEssay;
import com.billjc.speak.students.dao.StudentDao;

@Repository
public class EssayAppointmentDao extends BaseJdbcDaoEssay {
	final Logger logger = LoggerFactory.getLogger(StudentDao.class);

	/**
	 * 查找某学生最近预约的作文
	 * @param stuId 
	 * @param limit 返回结果数量
	 * @param essayType 作文类型
	 * @return
	 */
	public List<EssayAppointment> queryAppt(long stuId, long limit, String essayType) {
		String sql = "SELECT * FROM t_appoint where student_id=? AND type=? AND active=1 order by create_time desc limit ?";
		Object[] param = new Object[] { stuId, essayType, limit };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().query(sql, param,
				new EssayAppointmentRowMapper());
	}

	public int cancelAllAppt(List<Long> appointIdsForCancel) {
		String sql = "UPDATE t_appoint SET active = '0' WHERE appointment_id IN (?";
		for(int i=1;i<appointIdsForCancel.size();i++){
			sql+=",?";
		}
		sql+=");";
//		String ids = appointIdsForCancel.toString().replace("[", "(").replace("]", ")");
		Object[] param = appointIdsForCancel.toArray();
//		Map<String,Object> param = new HashMap<String,Object>();
//		param.put("list", appointIdsForCancel);
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().update(sql, param);
	}
}
