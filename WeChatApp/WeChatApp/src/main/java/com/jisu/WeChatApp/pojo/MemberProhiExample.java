package com.jisu.WeChatApp.pojo;

import java.util.ArrayList;
import java.util.List;

public class MemberProhiExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table member_prohi
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table member_prohi
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table member_prohi
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_prohi
     *
     * @mbg.generated
     */
    public MemberProhiExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_prohi
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_prohi
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_prohi
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_prohi
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_prohi
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_prohi
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_prohi
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_prohi
     *
     * @mbg.generated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_prohi
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_prohi
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table member_prohi
     *
     * @mbg.generated
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andMemberProhiIdIsNull() {
            addCriterion("member_prohi_id is null");
            return (Criteria) this;
        }

        public Criteria andMemberProhiIdIsNotNull() {
            addCriterion("member_prohi_id is not null");
            return (Criteria) this;
        }

        public Criteria andMemberProhiIdEqualTo(String value) {
            addCriterion("member_prohi_id =", value, "memberProhiId");
            return (Criteria) this;
        }

        public Criteria andMemberProhiIdNotEqualTo(String value) {
            addCriterion("member_prohi_id <>", value, "memberProhiId");
            return (Criteria) this;
        }

        public Criteria andMemberProhiIdGreaterThan(String value) {
            addCriterion("member_prohi_id >", value, "memberProhiId");
            return (Criteria) this;
        }

        public Criteria andMemberProhiIdGreaterThanOrEqualTo(String value) {
            addCriterion("member_prohi_id >=", value, "memberProhiId");
            return (Criteria) this;
        }

        public Criteria andMemberProhiIdLessThan(String value) {
            addCriterion("member_prohi_id <", value, "memberProhiId");
            return (Criteria) this;
        }

        public Criteria andMemberProhiIdLessThanOrEqualTo(String value) {
            addCriterion("member_prohi_id <=", value, "memberProhiId");
            return (Criteria) this;
        }

        public Criteria andMemberProhiIdLike(String value) {
            addCriterion("member_prohi_id like", value, "memberProhiId");
            return (Criteria) this;
        }

        public Criteria andMemberProhiIdNotLike(String value) {
            addCriterion("member_prohi_id not like", value, "memberProhiId");
            return (Criteria) this;
        }

        public Criteria andMemberProhiIdIn(List<String> values) {
            addCriterion("member_prohi_id in", values, "memberProhiId");
            return (Criteria) this;
        }

        public Criteria andMemberProhiIdNotIn(List<String> values) {
            addCriterion("member_prohi_id not in", values, "memberProhiId");
            return (Criteria) this;
        }

        public Criteria andMemberProhiIdBetween(String value1, String value2) {
            addCriterion("member_prohi_id between", value1, value2, "memberProhiId");
            return (Criteria) this;
        }

        public Criteria andMemberProhiIdNotBetween(String value1, String value2) {
            addCriterion("member_prohi_id not between", value1, value2, "memberProhiId");
            return (Criteria) this;
        }

        public Criteria andProhiStatusIsNull() {
            addCriterion("prohi_status is null");
            return (Criteria) this;
        }

        public Criteria andProhiStatusIsNotNull() {
            addCriterion("prohi_status is not null");
            return (Criteria) this;
        }

        public Criteria andProhiStatusEqualTo(Integer value) {
            addCriterion("prohi_status =", value, "prohiStatus");
            return (Criteria) this;
        }

        public Criteria andProhiStatusNotEqualTo(Integer value) {
            addCriterion("prohi_status <>", value, "prohiStatus");
            return (Criteria) this;
        }

        public Criteria andProhiStatusGreaterThan(Integer value) {
            addCriterion("prohi_status >", value, "prohiStatus");
            return (Criteria) this;
        }

        public Criteria andProhiStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("prohi_status >=", value, "prohiStatus");
            return (Criteria) this;
        }

        public Criteria andProhiStatusLessThan(Integer value) {
            addCriterion("prohi_status <", value, "prohiStatus");
            return (Criteria) this;
        }

        public Criteria andProhiStatusLessThanOrEqualTo(Integer value) {
            addCriterion("prohi_status <=", value, "prohiStatus");
            return (Criteria) this;
        }

        public Criteria andProhiStatusIn(List<Integer> values) {
            addCriterion("prohi_status in", values, "prohiStatus");
            return (Criteria) this;
        }

        public Criteria andProhiStatusNotIn(List<Integer> values) {
            addCriterion("prohi_status not in", values, "prohiStatus");
            return (Criteria) this;
        }

        public Criteria andProhiStatusBetween(Integer value1, Integer value2) {
            addCriterion("prohi_status between", value1, value2, "prohiStatus");
            return (Criteria) this;
        }

        public Criteria andProhiStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("prohi_status not between", value1, value2, "prohiStatus");
            return (Criteria) this;
        }

        public Criteria andProhiTypeIsNull() {
            addCriterion("prohi_type is null");
            return (Criteria) this;
        }

        public Criteria andProhiTypeIsNotNull() {
            addCriterion("prohi_type is not null");
            return (Criteria) this;
        }

        public Criteria andProhiTypeEqualTo(Integer value) {
            addCriterion("prohi_type =", value, "prohiType");
            return (Criteria) this;
        }

        public Criteria andProhiTypeNotEqualTo(Integer value) {
            addCriterion("prohi_type <>", value, "prohiType");
            return (Criteria) this;
        }

        public Criteria andProhiTypeGreaterThan(Integer value) {
            addCriterion("prohi_type >", value, "prohiType");
            return (Criteria) this;
        }

        public Criteria andProhiTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("prohi_type >=", value, "prohiType");
            return (Criteria) this;
        }

        public Criteria andProhiTypeLessThan(Integer value) {
            addCriterion("prohi_type <", value, "prohiType");
            return (Criteria) this;
        }

        public Criteria andProhiTypeLessThanOrEqualTo(Integer value) {
            addCriterion("prohi_type <=", value, "prohiType");
            return (Criteria) this;
        }

        public Criteria andProhiTypeIn(List<Integer> values) {
            addCriterion("prohi_type in", values, "prohiType");
            return (Criteria) this;
        }

        public Criteria andProhiTypeNotIn(List<Integer> values) {
            addCriterion("prohi_type not in", values, "prohiType");
            return (Criteria) this;
        }

        public Criteria andProhiTypeBetween(Integer value1, Integer value2) {
            addCriterion("prohi_type between", value1, value2, "prohiType");
            return (Criteria) this;
        }

        public Criteria andProhiTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("prohi_type not between", value1, value2, "prohiType");
            return (Criteria) this;
        }

        public Criteria andMemberNoIsNull() {
            addCriterion("member_no is null");
            return (Criteria) this;
        }

        public Criteria andMemberNoIsNotNull() {
            addCriterion("member_no is not null");
            return (Criteria) this;
        }

        public Criteria andMemberNoEqualTo(String value) {
            addCriterion("member_no =", value, "memberNo");
            return (Criteria) this;
        }

        public Criteria andMemberNoNotEqualTo(String value) {
            addCriterion("member_no <>", value, "memberNo");
            return (Criteria) this;
        }

        public Criteria andMemberNoGreaterThan(String value) {
            addCriterion("member_no >", value, "memberNo");
            return (Criteria) this;
        }

        public Criteria andMemberNoGreaterThanOrEqualTo(String value) {
            addCriterion("member_no >=", value, "memberNo");
            return (Criteria) this;
        }

        public Criteria andMemberNoLessThan(String value) {
            addCriterion("member_no <", value, "memberNo");
            return (Criteria) this;
        }

        public Criteria andMemberNoLessThanOrEqualTo(String value) {
            addCriterion("member_no <=", value, "memberNo");
            return (Criteria) this;
        }

        public Criteria andMemberNoLike(String value) {
            addCriterion("member_no like", value, "memberNo");
            return (Criteria) this;
        }

        public Criteria andMemberNoNotLike(String value) {
            addCriterion("member_no not like", value, "memberNo");
            return (Criteria) this;
        }

        public Criteria andMemberNoIn(List<String> values) {
            addCriterion("member_no in", values, "memberNo");
            return (Criteria) this;
        }

        public Criteria andMemberNoNotIn(List<String> values) {
            addCriterion("member_no not in", values, "memberNo");
            return (Criteria) this;
        }

        public Criteria andMemberNoBetween(String value1, String value2) {
            addCriterion("member_no between", value1, value2, "memberNo");
            return (Criteria) this;
        }

        public Criteria andMemberNoNotBetween(String value1, String value2) {
            addCriterion("member_no not between", value1, value2, "memberNo");
            return (Criteria) this;
        }

        public Criteria andMemberTypeIsNull() {
            addCriterion("member_type is null");
            return (Criteria) this;
        }

        public Criteria andMemberTypeIsNotNull() {
            addCriterion("member_type is not null");
            return (Criteria) this;
        }

        public Criteria andMemberTypeEqualTo(Integer value) {
            addCriterion("member_type =", value, "memberType");
            return (Criteria) this;
        }

        public Criteria andMemberTypeNotEqualTo(Integer value) {
            addCriterion("member_type <>", value, "memberType");
            return (Criteria) this;
        }

        public Criteria andMemberTypeGreaterThan(Integer value) {
            addCriterion("member_type >", value, "memberType");
            return (Criteria) this;
        }

        public Criteria andMemberTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("member_type >=", value, "memberType");
            return (Criteria) this;
        }

        public Criteria andMemberTypeLessThan(Integer value) {
            addCriterion("member_type <", value, "memberType");
            return (Criteria) this;
        }

        public Criteria andMemberTypeLessThanOrEqualTo(Integer value) {
            addCriterion("member_type <=", value, "memberType");
            return (Criteria) this;
        }

        public Criteria andMemberTypeIn(List<Integer> values) {
            addCriterion("member_type in", values, "memberType");
            return (Criteria) this;
        }

        public Criteria andMemberTypeNotIn(List<Integer> values) {
            addCriterion("member_type not in", values, "memberType");
            return (Criteria) this;
        }

        public Criteria andMemberTypeBetween(Integer value1, Integer value2) {
            addCriterion("member_type between", value1, value2, "memberType");
            return (Criteria) this;
        }

        public Criteria andMemberTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("member_type not between", value1, value2, "memberType");
            return (Criteria) this;
        }

        public Criteria andProhiTimeIsNull() {
            addCriterion("prohi_time is null");
            return (Criteria) this;
        }

        public Criteria andProhiTimeIsNotNull() {
            addCriterion("prohi_time is not null");
            return (Criteria) this;
        }

        public Criteria andProhiTimeEqualTo(Integer value) {
            addCriterion("prohi_time =", value, "prohiTime");
            return (Criteria) this;
        }

        public Criteria andProhiTimeNotEqualTo(Integer value) {
            addCriterion("prohi_time <>", value, "prohiTime");
            return (Criteria) this;
        }

        public Criteria andProhiTimeGreaterThan(Integer value) {
            addCriterion("prohi_time >", value, "prohiTime");
            return (Criteria) this;
        }

        public Criteria andProhiTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("prohi_time >=", value, "prohiTime");
            return (Criteria) this;
        }

        public Criteria andProhiTimeLessThan(Integer value) {
            addCriterion("prohi_time <", value, "prohiTime");
            return (Criteria) this;
        }

        public Criteria andProhiTimeLessThanOrEqualTo(Integer value) {
            addCriterion("prohi_time <=", value, "prohiTime");
            return (Criteria) this;
        }

        public Criteria andProhiTimeIn(List<Integer> values) {
            addCriterion("prohi_time in", values, "prohiTime");
            return (Criteria) this;
        }

        public Criteria andProhiTimeNotIn(List<Integer> values) {
            addCriterion("prohi_time not in", values, "prohiTime");
            return (Criteria) this;
        }

        public Criteria andProhiTimeBetween(Integer value1, Integer value2) {
            addCriterion("prohi_time between", value1, value2, "prohiTime");
            return (Criteria) this;
        }

        public Criteria andProhiTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("prohi_time not between", value1, value2, "prohiTime");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table member_prohi
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table member_prohi
     *
     * @mbg.generated
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}