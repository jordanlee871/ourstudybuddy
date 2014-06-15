package com.billjc.speak.sk_class.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.billjc.framework.BaseJdbcDao;
import com.billjc.framework.util.DateUtil;
import com.billjc.speak.myClass.entity.MyClass;
import com.billjc.speak.myClass.entity.mapper.MyClassRowMapper;
import com.billjc.speak.sk_class.entity.SkClass;
import com.billjc.speak.sk_class.entity.mapper.SkClassRowMapper;
import com.billjc.speak.teacher.entity.Teacher;

@Repository
public class SkClassDao extends BaseJdbcDao {
	final Logger logger = LoggerFactory.getLogger(SkClassDao.class);

	/**
	 * 新增一个课程
	 * 
	 * @param skClass
	 * @return int
	 */
	public int insertClass(SkClass skClass) {

		String sql = "INSERT INTO sk_class(STU_ID,TEA_ID,STU_LATE,TEA_LATE,CLS_TYPE,CLS_LENGTH,CLS_BEGIN_TIME,CLS_COMMENT,BOOKING_REMARK,CLS_STATUS ) VALUES(?,?,?,?,?,?,?,?,?,?)";

		Object[] param = new Object[] { skClass.getStuId(), skClass.getTeaId(),
				skClass.getStuLate(), skClass.getTeaLate(),
				skClass.getClsType(), skClass.getClsLength(),
				skClass.getBeginTime(), skClass.getClsComment(),
				skClass.getBookingRemark(), skClass.getClsStatus() };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().update(sql, param);

	}

	/**
	 * 更新一个课程
	 * 
	 * @param s
	 * 
	 * @return int
	 */
	public int updateClass(SkClass s) {

		String sql = "UPDATE  sk_class SET STU_ID=?,STU_LATE=?,TEA_ID=?,TEA_LATE=?,BOOKING_REMARK=?,CLS_BEGIN_TIME=?"
				+ ",CLS_TYPE=?,CLS_LENGTH=?,CLS_STATUS=?,CLS_COMMENT=?  WHERE CLS_ID=? ";

		Object[] param = new Object[] { s.getStuId(), s.getStuLate(),
				s.getTeaId(), s.getTeaLate(), s.getBookingRemark(),
				s.getBeginTime(), s.getClsType(), s.getClsLength(),
				s.getClsStatus(), s.getClsComment(), s.getClsId() };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().update(sql, param);
	}
	
	/**
	 * 根据时间和老师，查询课程是否被预约
	 * @param teaId
	 * @param beginTime
	 * @return
	 */
	public List<SkClass> queryClassByTime(Long teaId, Date beginTime){
		String sql = "SELECT * FROM `sk_class` WHERE `tea_id`=? AND `cls_begin_time`=? AND CLS_STATUS=0";
		
		Object[] param = new Object[] { teaId, DateUtil.getDateMinuteFormat(beginTime)+":00" };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		
		return this.getJdbcTemplate().query(sql, param, new SkClassRowMapper());
	}
	
	/**
	 * 根据时间和老师，查询课程是否被预约
	 * @param teaId
	 * @param beginTime
	 * @return
	 */
	public List<SkClass> queryNormalClass(Long teaId, Date beginTime){
		String sql = "SELECT * FROM `sk_class` WHERE `tea_id`=? AND `cls_begin_time`=? AND CLS_STATUS=0";
		
		Object[] param = new Object[] { teaId, DateUtil.getDateMinuteFormat(beginTime)+":00" };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		
		return this.getJdbcTemplate().query(sql, param, new SkClassRowMapper());
	}
	
	/**
	 * 查询预约课程ID
	 * 
	 * @param s
	 * 
	 * 
	 * @return int
	 */
	 public List<SkClass> queryClsIdByCls(SkClass s) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM sk_class WHERE STU_ID=? AND TEA_ID=? AND CLS_LENGTH=? AND CLS_TYPE=? AND CLS_BEGIN_TIME=? AND CLS_STATUS=0";
		//String sql = "SELECT * FROM sk_class WHERE STU_ID=? AND TEA_ID=? AND CLS_LENGTH=? AND CLS_TYPE=? AND CLS_BEGIN_TIME=DATE_FORMAT(?,'%Y-%m-%d %H:%S:00') AND CLS_STATUS=0";
		
		Object[] param = new Object[] { s.getStuId(), s.getTeaId(),
				s.getClsLength(), s.getClsType(), DateUtil.getDateMinuteFormat(s.getBeginTime())+":00" };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		
		return this.getJdbcTemplate().query(sql, param, new SkClassRowMapper());
	}
	 
	/**
	 * 查询预约课程ID
	 * 
	 * @param s
	 * 
	 * 
	 * @return int
	 */
	public SkClass queryNearlyTimeClassByStudent(long stuId) {
		// TODO Auto-generated method stub

		String sql = "SELECT MAX(SC.CLS_BEGIN_TIME) AS  CLS_BEGIN_TIME,SC.BOOKING_REMARK,SC.CLS_ID,SC.CLS_COMMENT,SC.CLS_LENGTH,SC.CLS_STATUS,SC.CLS_TYPE,SC.STU_ID,SC.STU_LATE,SC.TEA_ID,SC.TEA_LATE FROM sk_student SS,sk_class SC  WHERE SS.STU_ID=? AND SC.STU_ID=SS.STU_ID ";

		Object[] param = new Object[] { stuId};
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.queryForObject(sql, param, new SkClassRowMapper());
	}
	
	/**
	 * 根据Id查询预约课程
	 * 
	 * @param clsId
	 * @return SkClass
	 */
	public SkClass queryByClsId(Long clsId) {

		String sql = " SELECT SC.* ,SS.WW_NUM ,SU.USER_NAME FROM sk_class SC,sk_student SS,sk_teacher ST,sk_user SU WHERE SC.CLS_ID=? AND SS.STU_ID=SC.STU_ID AND ST.TEA_ID=SC.TEA_ID  AND SU.USER_ID=ST.USER_ID";
		Object[] param = new Object[] { clsId };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.queryForObject(sql, param, new SkClassRowMapper(true));
	}

	/**
	 * 查询课程历史
	 * 
	 * @param start
	 * @param end
	 * @return List<user>
	 */
	public List<SkClass> queryList(String CONDITION, String ORDERBY, int start,
			int end) {
		String sql = "SELECT SC.*,SU.USER_NAME,SS.`ename` AS WW_NUM FROM sk_class SC ,sk_student SS,sk_teacher ST,sk_user SU WHERE SS.STU_ID=SC.STU_ID AND"
				+ " ST.TEA_ID=SC.TEA_ID  AND SU.USER_ID=ST.USER_ID " + CONDITION + ORDERBY + " LIMIT ?,?";
		Object[] param = new Object[] { start, end };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate()
				.query(sql, param, new SkClassRowMapper(true));
	}

	/**
	 * 课程历史数目
	 * 
	 * @param condition
	 * @return int
	 */
	public int queryListCount(String condition) {
		return this.getJdbcTemplate().queryForInt(
				"SELECT COUNT(*) FROM sk_class SC ,sk_student SS,sk_teacher ST   WHERE  SS.STU_ID=SC.STU_ID AND  ST.TEA_ID=SC.TEA_ID " + condition);
	}

	
	/**
	 * 统计课程
	 * 
	 * @param teaId
	 * @return Map
	 */
	public Map<String,String> count(long teaId,String condition) {
		
		
		
		
		Map<String,String > map=new HashMap<String, String >	();
		String sql1="SELECT COUNT(*) FROM sk_class WHERE CLS_STATUS=1 AND CLS_TYPE=1  AND TEA_ID=? "+condition;
		String sql2="SELECT COUNT(*) FROM sk_class WHERE CLS_STATUS=1 AND CLS_TYPE=2  AND TEA_ID=? "+condition;
		String sql3="SELECT COUNT(*) FROM sk_class WHERE CLS_STATUS=1 AND CLS_TYPE=3 AND TEA_ID=? "+condition;
		String sql4="SELECT COUNT(*) FROM sk_class WHERE CLS_STATUS=1 AND CLS_TYPE=4  AND TEA_ID=? "+condition;
		
		String sql11="SELECT SUM(CLS_LENGTH)  FROM sk_class WHERE CLS_STATUS=1 AND CLS_TYPE=1  AND TEA_ID=? "+condition;
		String sql21="SELECT SUM(CLS_LENGTH)  FROM sk_class WHERE CLS_STATUS=1 AND CLS_TYPE=2  AND TEA_ID=? "+condition;
		String sql31="SELECT SUM(CLS_LENGTH)  FROM sk_class WHERE CLS_STATUS=1 AND CLS_TYPE=3 AND TEA_ID=? "+condition;
		String sql41="SELECT SUM(CLS_LENGTH)  FROM sk_class WHERE CLS_STATUS=1 AND CLS_TYPE=4  AND TEA_ID=? "+condition;
		
		String sql5="SELECT COUNT(*) FROM sk_class WHERE CLS_STATUS=2 AND TEA_ID=? "+condition;
		String sql6="SELECT COUNT(*) FROM sk_class WHERE CLS_STATUS=3 AND TEA_ID=? "+condition;
		String sql7="SELECT COUNT(*) FROM sk_class WHERE CLS_STATUS=4 AND TEA_ID=? "+condition;
		String sql8="SELECT COUNT(*) FROM sk_class WHERE CLS_STATUS=5  AND TEA_ID=? "+condition;
		String sql9="SELECT COUNT(*) FROM sk_class WHERE CLS_STATUS=6  AND TEA_ID=? "+condition;
		String sql10="SELECT COUNT(*) FROM sk_class WHERE CLS_STATUS=7 AND TEA_ID=? "+condition;
		
		String sql0="SELECT COUNT(*) FROM sk_class WHERE CLS_STATUS=0 AND TEA_ID=? "+condition;
		
		Object[] param = new Object[] { teaId };
		logger.info(sql1.replaceAll("\\?", "{}"), param);
		logger.info(sql2.replaceAll("\\?", "{}"), param);
		logger.info(sql3.replaceAll("\\?", "{}"), param);
		logger.info(sql4.replaceAll("\\?", "{}"), param);
		logger.info(sql5.replaceAll("\\?", "{}"), param);
		logger.info(sql6.replaceAll("\\?", "{}"), param);
		logger.info(sql7.replaceAll("\\?", "{}"), param);
		logger.info(sql8.replaceAll("\\?", "{}"), param);
		logger.info(sql9.replaceAll("\\?", "{}"), param);
		logger.info(sql10.replaceAll("\\?", "{}"), param);
		map.put("ieCount", this.getJdbcTemplate().queryForInt(sql1,param)+"");
		map.put("toCount", this.getJdbcTemplate().queryForInt(sql2,param)+"");
		map.put("trCount", this.getJdbcTemplate().queryForInt(sql3,param)+"");
		map.put("otCount", this.getJdbcTemplate().queryForInt(sql4,param)+"");
		
		map.put("ieCount1", this.getJdbcTemplate().queryForObject(sql11,param,Float.class)!=null?this.getJdbcTemplate().queryForObject(sql11,param,Float.class)+"":"0");
		map.put("toCount1", this.getJdbcTemplate().queryForObject(sql21,param,Float.class)!=null?this.getJdbcTemplate().queryForObject(sql21,param,Float.class)+"":"0");
		map.put("trCount1", this.getJdbcTemplate().queryForObject(sql31,param,Float.class)!=null?this.getJdbcTemplate().queryForObject(sql31,param,Float.class)+"":"0");
		map.put("otCount1", this.getJdbcTemplate().queryForObject(sql41,param,Float.class)!=null?this.getJdbcTemplate().queryForObject(sql41,param,Float.class)+"":"0");
		
		map.put("scCount", this.getJdbcTemplate().queryForInt(sql5,param)+"");
		map.put("tcCount", this.getJdbcTemplate().queryForInt(sql6,param)+"");
		map.put("secCount", this.getJdbcTemplate().queryForInt(sql7,param)+"");
		map.put("tecCount", this.getJdbcTemplate().queryForInt(sql8,param)+"");
		map.put("saCount", this.getJdbcTemplate().queryForInt(sql9,param)+"");
		map.put("taCount", this.getJdbcTemplate().queryForInt(sql10,param)+"");
		map.put("PeCount", this.getJdbcTemplate().queryForInt(sql0,param)+"");
		
		
		
		return map;
	}
	
	/**
	 * 查询老师的 过去的Pending课程
	 * 
	 * @param teaId 
	 * @return 课程
	 */
	public List<SkClass> queryPastPendingClasses(Long teaId) {
		String sql = "SELECT SC . *, SU.USER_NAME, SS.`ename` AS WW_NUM FROM sk_class SC, sk_student SS, sk_teacher ST, sk_user SU WHERE SS.STU_ID = SC.STU_ID AND ST.TEA_ID = SC.TEA_ID AND SU.USER_ID = ST.USER_ID AND SC.TEA_ID = ? AND  SC.CLS_STATUS=0 AND SC.CLS_BEGIN_TIME < NOW() ORDER BY SC.CLS_BEGIN_TIME";
		Object[] param = new Object[] { teaId  };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().query(sql, param, new SkClassRowMapper(true));
		
	}
	/**
	 * 查询老师的 过去的Pending课程
	 * 
	 * @param teaId 
	 * @return 课程
	 */
	public List<SkClass> queryFucturePendingClasses(Long teaId) {
		String sql = "SELECT SC . *, SU.USER_NAME, SS.`ename` AS WW_NUM FROM sk_class SC, sk_student SS, sk_teacher ST, sk_user SU WHERE SS.STU_ID = SC.STU_ID AND ST.TEA_ID = SC.TEA_ID AND SU.USER_ID = ST.USER_ID AND SC.TEA_ID = ? AND  SC.CLS_STATUS=0 AND SC.CLS_BEGIN_TIME >= NOW() ORDER BY SC.CLS_BEGIN_TIME";
		Object[] param = new Object[] { teaId  };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().query(sql, param, new SkClassRowMapper(true));
		
	}
	/**
	 * 查询老师的 非 Pending课程
	 * 
	 * @param teaId 
	 * @param start
	 * @param end
	 * @return 课程
	 */
	public List<SkClass> queryAllNonPendingClasses(Long teaId,int star,int end){
		String sql = "SELECT SC . *, SU.USER_NAME, SS.`ename` AS WW_NUM FROM sk_class SC, sk_student SS, sk_teacher ST, sk_user SU WHERE SS.STU_ID = SC.STU_ID AND ST.TEA_ID = SC.TEA_ID AND SU.USER_ID = ST.USER_ID AND SC.TEA_ID = ? AND  SC.CLS_STATUS<>0 ORDER BY SC.CLS_BEGIN_TIME DESC LIMIT ?,?";
		Object[] param = new Object[] { teaId,star,end };
		return this.getJdbcTemplate().query(sql, param, new SkClassRowMapper(true));
		
	}
}
