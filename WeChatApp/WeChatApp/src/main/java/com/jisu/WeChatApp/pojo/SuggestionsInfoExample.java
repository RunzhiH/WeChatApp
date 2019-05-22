package com.jisu.WeChatApp.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SuggestionsInfoExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table suggestions_info
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table suggestions_info
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table suggestions_info
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table suggestions_info
     *
     * @mbg.generated
     */
    public SuggestionsInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table suggestions_info
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table suggestions_info
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table suggestions_info
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table suggestions_info
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table suggestions_info
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table suggestions_info
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table suggestions_info
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
     * This method corresponds to the database table suggestions_info
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
     * This method corresponds to the database table suggestions_info
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table suggestions_info
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
     * This class corresponds to the database table suggestions_info
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

        public Criteria andSuggestionsIdIsNull() {
            addCriterion("suggestions_id is null");
            return (Criteria) this;
        }

        public Criteria andSuggestionsIdIsNotNull() {
            addCriterion("suggestions_id is not null");
            return (Criteria) this;
        }

        public Criteria andSuggestionsIdEqualTo(String value) {
            addCriterion("suggestions_id =", value, "suggestionsId");
            return (Criteria) this;
        }

        public Criteria andSuggestionsIdNotEqualTo(String value) {
            addCriterion("suggestions_id <>", value, "suggestionsId");
            return (Criteria) this;
        }

        public Criteria andSuggestionsIdGreaterThan(String value) {
            addCriterion("suggestions_id >", value, "suggestionsId");
            return (Criteria) this;
        }

        public Criteria andSuggestionsIdGreaterThanOrEqualTo(String value) {
            addCriterion("suggestions_id >=", value, "suggestionsId");
            return (Criteria) this;
        }

        public Criteria andSuggestionsIdLessThan(String value) {
            addCriterion("suggestions_id <", value, "suggestionsId");
            return (Criteria) this;
        }

        public Criteria andSuggestionsIdLessThanOrEqualTo(String value) {
            addCriterion("suggestions_id <=", value, "suggestionsId");
            return (Criteria) this;
        }

        public Criteria andSuggestionsIdLike(String value) {
            addCriterion("suggestions_id like", value, "suggestionsId");
            return (Criteria) this;
        }

        public Criteria andSuggestionsIdNotLike(String value) {
            addCriterion("suggestions_id not like", value, "suggestionsId");
            return (Criteria) this;
        }

        public Criteria andSuggestionsIdIn(List<String> values) {
            addCriterion("suggestions_id in", values, "suggestionsId");
            return (Criteria) this;
        }

        public Criteria andSuggestionsIdNotIn(List<String> values) {
            addCriterion("suggestions_id not in", values, "suggestionsId");
            return (Criteria) this;
        }

        public Criteria andSuggestionsIdBetween(String value1, String value2) {
            addCriterion("suggestions_id between", value1, value2, "suggestionsId");
            return (Criteria) this;
        }

        public Criteria andSuggestionsIdNotBetween(String value1, String value2) {
            addCriterion("suggestions_id not between", value1, value2, "suggestionsId");
            return (Criteria) this;
        }

        public Criteria andSuggestionsTextIsNull() {
            addCriterion("suggestions_text is null");
            return (Criteria) this;
        }

        public Criteria andSuggestionsTextIsNotNull() {
            addCriterion("suggestions_text is not null");
            return (Criteria) this;
        }

        public Criteria andSuggestionsTextEqualTo(String value) {
            addCriterion("suggestions_text =", value, "suggestionsText");
            return (Criteria) this;
        }

        public Criteria andSuggestionsTextNotEqualTo(String value) {
            addCriterion("suggestions_text <>", value, "suggestionsText");
            return (Criteria) this;
        }

        public Criteria andSuggestionsTextGreaterThan(String value) {
            addCriterion("suggestions_text >", value, "suggestionsText");
            return (Criteria) this;
        }

        public Criteria andSuggestionsTextGreaterThanOrEqualTo(String value) {
            addCriterion("suggestions_text >=", value, "suggestionsText");
            return (Criteria) this;
        }

        public Criteria andSuggestionsTextLessThan(String value) {
            addCriterion("suggestions_text <", value, "suggestionsText");
            return (Criteria) this;
        }

        public Criteria andSuggestionsTextLessThanOrEqualTo(String value) {
            addCriterion("suggestions_text <=", value, "suggestionsText");
            return (Criteria) this;
        }

        public Criteria andSuggestionsTextLike(String value) {
            addCriterion("suggestions_text like", value, "suggestionsText");
            return (Criteria) this;
        }

        public Criteria andSuggestionsTextNotLike(String value) {
            addCriterion("suggestions_text not like", value, "suggestionsText");
            return (Criteria) this;
        }

        public Criteria andSuggestionsTextIn(List<String> values) {
            addCriterion("suggestions_text in", values, "suggestionsText");
            return (Criteria) this;
        }

        public Criteria andSuggestionsTextNotIn(List<String> values) {
            addCriterion("suggestions_text not in", values, "suggestionsText");
            return (Criteria) this;
        }

        public Criteria andSuggestionsTextBetween(String value1, String value2) {
            addCriterion("suggestions_text between", value1, value2, "suggestionsText");
            return (Criteria) this;
        }

        public Criteria andSuggestionsTextNotBetween(String value1, String value2) {
            addCriterion("suggestions_text not between", value1, value2, "suggestionsText");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andContactAwayIsNull() {
            addCriterion("contact_away is null");
            return (Criteria) this;
        }

        public Criteria andContactAwayIsNotNull() {
            addCriterion("contact_away is not null");
            return (Criteria) this;
        }

        public Criteria andContactAwayEqualTo(String value) {
            addCriterion("contact_away =", value, "contactAway");
            return (Criteria) this;
        }

        public Criteria andContactAwayNotEqualTo(String value) {
            addCriterion("contact_away <>", value, "contactAway");
            return (Criteria) this;
        }

        public Criteria andContactAwayGreaterThan(String value) {
            addCriterion("contact_away >", value, "contactAway");
            return (Criteria) this;
        }

        public Criteria andContactAwayGreaterThanOrEqualTo(String value) {
            addCriterion("contact_away >=", value, "contactAway");
            return (Criteria) this;
        }

        public Criteria andContactAwayLessThan(String value) {
            addCriterion("contact_away <", value, "contactAway");
            return (Criteria) this;
        }

        public Criteria andContactAwayLessThanOrEqualTo(String value) {
            addCriterion("contact_away <=", value, "contactAway");
            return (Criteria) this;
        }

        public Criteria andContactAwayLike(String value) {
            addCriterion("contact_away like", value, "contactAway");
            return (Criteria) this;
        }

        public Criteria andContactAwayNotLike(String value) {
            addCriterion("contact_away not like", value, "contactAway");
            return (Criteria) this;
        }

        public Criteria andContactAwayIn(List<String> values) {
            addCriterion("contact_away in", values, "contactAway");
            return (Criteria) this;
        }

        public Criteria andContactAwayNotIn(List<String> values) {
            addCriterion("contact_away not in", values, "contactAway");
            return (Criteria) this;
        }

        public Criteria andContactAwayBetween(String value1, String value2) {
            addCriterion("contact_away between", value1, value2, "contactAway");
            return (Criteria) this;
        }

        public Criteria andContactAwayNotBetween(String value1, String value2) {
            addCriterion("contact_away not between", value1, value2, "contactAway");
            return (Criteria) this;
        }

        public Criteria andSuggestionsTypeIsNull() {
            addCriterion("suggestions_type is null");
            return (Criteria) this;
        }

        public Criteria andSuggestionsTypeIsNotNull() {
            addCriterion("suggestions_type is not null");
            return (Criteria) this;
        }

        public Criteria andSuggestionsTypeEqualTo(Integer value) {
            addCriterion("suggestions_type =", value, "suggestionsType");
            return (Criteria) this;
        }

        public Criteria andSuggestionsTypeNotEqualTo(Integer value) {
            addCriterion("suggestions_type <>", value, "suggestionsType");
            return (Criteria) this;
        }

        public Criteria andSuggestionsTypeGreaterThan(Integer value) {
            addCriterion("suggestions_type >", value, "suggestionsType");
            return (Criteria) this;
        }

        public Criteria andSuggestionsTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("suggestions_type >=", value, "suggestionsType");
            return (Criteria) this;
        }

        public Criteria andSuggestionsTypeLessThan(Integer value) {
            addCriterion("suggestions_type <", value, "suggestionsType");
            return (Criteria) this;
        }

        public Criteria andSuggestionsTypeLessThanOrEqualTo(Integer value) {
            addCriterion("suggestions_type <=", value, "suggestionsType");
            return (Criteria) this;
        }

        public Criteria andSuggestionsTypeIn(List<Integer> values) {
            addCriterion("suggestions_type in", values, "suggestionsType");
            return (Criteria) this;
        }

        public Criteria andSuggestionsTypeNotIn(List<Integer> values) {
            addCriterion("suggestions_type not in", values, "suggestionsType");
            return (Criteria) this;
        }

        public Criteria andSuggestionsTypeBetween(Integer value1, Integer value2) {
            addCriterion("suggestions_type between", value1, value2, "suggestionsType");
            return (Criteria) this;
        }

        public Criteria andSuggestionsTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("suggestions_type not between", value1, value2, "suggestionsType");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table suggestions_info
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
     * This class corresponds to the database table suggestions_info
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