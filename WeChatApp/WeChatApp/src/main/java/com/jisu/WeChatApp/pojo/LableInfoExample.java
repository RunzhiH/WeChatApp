package com.jisu.WeChatApp.pojo;

import java.util.ArrayList;
import java.util.List;

public class LableInfoExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table lable_info
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table lable_info
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table lable_info
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lable_info
     *
     * @mbg.generated
     */
    public LableInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lable_info
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lable_info
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lable_info
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lable_info
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lable_info
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lable_info
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lable_info
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
     * This method corresponds to the database table lable_info
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
     * This method corresponds to the database table lable_info
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lable_info
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
     * This class corresponds to the database table lable_info
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

        public Criteria andLableIdIsNull() {
            addCriterion("lable_id is null");
            return (Criteria) this;
        }

        public Criteria andLableIdIsNotNull() {
            addCriterion("lable_id is not null");
            return (Criteria) this;
        }

        public Criteria andLableIdEqualTo(String value) {
            addCriterion("lable_id =", value, "lableId");
            return (Criteria) this;
        }

        public Criteria andLableIdNotEqualTo(String value) {
            addCriterion("lable_id <>", value, "lableId");
            return (Criteria) this;
        }

        public Criteria andLableIdGreaterThan(String value) {
            addCriterion("lable_id >", value, "lableId");
            return (Criteria) this;
        }

        public Criteria andLableIdGreaterThanOrEqualTo(String value) {
            addCriterion("lable_id >=", value, "lableId");
            return (Criteria) this;
        }

        public Criteria andLableIdLessThan(String value) {
            addCriterion("lable_id <", value, "lableId");
            return (Criteria) this;
        }

        public Criteria andLableIdLessThanOrEqualTo(String value) {
            addCriterion("lable_id <=", value, "lableId");
            return (Criteria) this;
        }

        public Criteria andLableIdLike(String value) {
            addCriterion("lable_id like", value, "lableId");
            return (Criteria) this;
        }

        public Criteria andLableIdNotLike(String value) {
            addCriterion("lable_id not like", value, "lableId");
            return (Criteria) this;
        }

        public Criteria andLableIdIn(List<String> values) {
            addCriterion("lable_id in", values, "lableId");
            return (Criteria) this;
        }

        public Criteria andLableIdNotIn(List<String> values) {
            addCriterion("lable_id not in", values, "lableId");
            return (Criteria) this;
        }

        public Criteria andLableIdBetween(String value1, String value2) {
            addCriterion("lable_id between", value1, value2, "lableId");
            return (Criteria) this;
        }

        public Criteria andLableIdNotBetween(String value1, String value2) {
            addCriterion("lable_id not between", value1, value2, "lableId");
            return (Criteria) this;
        }

        public Criteria andLableNameIsNull() {
            addCriterion("lable_name is null");
            return (Criteria) this;
        }

        public Criteria andLableNameIsNotNull() {
            addCriterion("lable_name is not null");
            return (Criteria) this;
        }

        public Criteria andLableNameEqualTo(String value) {
            addCriterion("lable_name =", value, "lableName");
            return (Criteria) this;
        }

        public Criteria andLableNameNotEqualTo(String value) {
            addCriterion("lable_name <>", value, "lableName");
            return (Criteria) this;
        }

        public Criteria andLableNameGreaterThan(String value) {
            addCriterion("lable_name >", value, "lableName");
            return (Criteria) this;
        }

        public Criteria andLableNameGreaterThanOrEqualTo(String value) {
            addCriterion("lable_name >=", value, "lableName");
            return (Criteria) this;
        }

        public Criteria andLableNameLessThan(String value) {
            addCriterion("lable_name <", value, "lableName");
            return (Criteria) this;
        }

        public Criteria andLableNameLessThanOrEqualTo(String value) {
            addCriterion("lable_name <=", value, "lableName");
            return (Criteria) this;
        }

        public Criteria andLableNameLike(String value) {
            addCriterion("lable_name like", value, "lableName");
            return (Criteria) this;
        }

        public Criteria andLableNameNotLike(String value) {
            addCriterion("lable_name not like", value, "lableName");
            return (Criteria) this;
        }

        public Criteria andLableNameIn(List<String> values) {
            addCriterion("lable_name in", values, "lableName");
            return (Criteria) this;
        }

        public Criteria andLableNameNotIn(List<String> values) {
            addCriterion("lable_name not in", values, "lableName");
            return (Criteria) this;
        }

        public Criteria andLableNameBetween(String value1, String value2) {
            addCriterion("lable_name between", value1, value2, "lableName");
            return (Criteria) this;
        }

        public Criteria andLableNameNotBetween(String value1, String value2) {
            addCriterion("lable_name not between", value1, value2, "lableName");
            return (Criteria) this;
        }

        public Criteria andLableDescIsNull() {
            addCriterion("lable_desc is null");
            return (Criteria) this;
        }

        public Criteria andLableDescIsNotNull() {
            addCriterion("lable_desc is not null");
            return (Criteria) this;
        }

        public Criteria andLableDescEqualTo(String value) {
            addCriterion("lable_desc =", value, "lableDesc");
            return (Criteria) this;
        }

        public Criteria andLableDescNotEqualTo(String value) {
            addCriterion("lable_desc <>", value, "lableDesc");
            return (Criteria) this;
        }

        public Criteria andLableDescGreaterThan(String value) {
            addCriterion("lable_desc >", value, "lableDesc");
            return (Criteria) this;
        }

        public Criteria andLableDescGreaterThanOrEqualTo(String value) {
            addCriterion("lable_desc >=", value, "lableDesc");
            return (Criteria) this;
        }

        public Criteria andLableDescLessThan(String value) {
            addCriterion("lable_desc <", value, "lableDesc");
            return (Criteria) this;
        }

        public Criteria andLableDescLessThanOrEqualTo(String value) {
            addCriterion("lable_desc <=", value, "lableDesc");
            return (Criteria) this;
        }

        public Criteria andLableDescLike(String value) {
            addCriterion("lable_desc like", value, "lableDesc");
            return (Criteria) this;
        }

        public Criteria andLableDescNotLike(String value) {
            addCriterion("lable_desc not like", value, "lableDesc");
            return (Criteria) this;
        }

        public Criteria andLableDescIn(List<String> values) {
            addCriterion("lable_desc in", values, "lableDesc");
            return (Criteria) this;
        }

        public Criteria andLableDescNotIn(List<String> values) {
            addCriterion("lable_desc not in", values, "lableDesc");
            return (Criteria) this;
        }

        public Criteria andLableDescBetween(String value1, String value2) {
            addCriterion("lable_desc between", value1, value2, "lableDesc");
            return (Criteria) this;
        }

        public Criteria andLableDescNotBetween(String value1, String value2) {
            addCriterion("lable_desc not between", value1, value2, "lableDesc");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table lable_info
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
     * This class corresponds to the database table lable_info
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