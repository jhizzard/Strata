/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.platform.finance.swap;

import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.ImmutableDefaults;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.basics.date.BusinessDayAdjustment;
import com.opengamma.basics.schedule.Frequency;

/**
 * Defines the schedule of fixing dates relative to the accrual periods.
 * <p>
 * This defines the data necessary to create a schedule of reset periods which
 * are used to locate the fixing dates.
 * <p>
 * Each accrual period contains one or more reset periods.
 * If an accrual period contains more than one reset period then the averaging
 * method will be used to combine the floating rates.
 * <p>
 * This class defines reset periods using a periodic frequency.
 * The frequency must match or be smaller than the accrual periodic frequency.
 * The reset schedule is calculated forwards, potentially with a short stub at the end.
 */
@BeanDefinition
public final class ResetSchedule
    implements ImmutableBean, Serializable {

  /** Serialization version. */
  private static final long serialVersionUID = 1L;

  /**
   * The periodic frequency of reset dates.
   * <p>
   * Reset dates will be calculated within each accrual period based on unadjusted dates.
   * The frequency must be the same as, or smaller than, the accrual periodic frequency.
   * When calculating the reset dates, the roll convention of the accrual periods will be used.
   * Once the unadjusted date calculation is complete, the business day adjustment specified
   * here will be used.
   * <p>
   * Averaging applies if the reset frequency does not equal the accrual frequency.
   */
  @PropertyDefinition(validate = "notNull")
  private final Frequency resetFrequency;
  /**
   * The business day adjustment to apply to each reset date.
   * <p>
   * This adjustment is applied to each reset date to ensure it is a valid business day.
   */
  @PropertyDefinition(validate = "notNull")
  private final BusinessDayAdjustment resetBusinessDayAdjustment;
  /**
   * The rate averaging method, defaulted to 'Unweighted'.
   * <p>
   * This is used when more than one fixing contributes to the accrual period.
   * <p>
   * Averaging may be weighted by the number of days that the fixing is applicable for.
   * The number of days is based on the reset period, not the period between two fixing dates.
   * <p>
   * Defined by the 2006 ISDA definitions article 6.2a.
   */
  @PropertyDefinition(validate = "notNull")
  private final RateAveragingMethod rateAveragingMethod;

  //-------------------------------------------------------------------------
  @ImmutableDefaults
  private static void applyDefaults(Builder builder) {
    builder.rateAveragingMethod(RateAveragingMethod.UNWEIGHTED);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code ResetSchedule}.
   * @return the meta-bean, not null
   */
  public static ResetSchedule.Meta meta() {
    return ResetSchedule.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(ResetSchedule.Meta.INSTANCE);
  }

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static ResetSchedule.Builder builder() {
    return new ResetSchedule.Builder();
  }

  private ResetSchedule(
      Frequency resetFrequency,
      BusinessDayAdjustment resetBusinessDayAdjustment,
      RateAveragingMethod rateAveragingMethod) {
    JodaBeanUtils.notNull(resetFrequency, "resetFrequency");
    JodaBeanUtils.notNull(resetBusinessDayAdjustment, "resetBusinessDayAdjustment");
    JodaBeanUtils.notNull(rateAveragingMethod, "rateAveragingMethod");
    this.resetFrequency = resetFrequency;
    this.resetBusinessDayAdjustment = resetBusinessDayAdjustment;
    this.rateAveragingMethod = rateAveragingMethod;
  }

  @Override
  public ResetSchedule.Meta metaBean() {
    return ResetSchedule.Meta.INSTANCE;
  }

  @Override
  public <R> Property<R> property(String propertyName) {
    return metaBean().<R>metaProperty(propertyName).createProperty(this);
  }

  @Override
  public Set<String> propertyNames() {
    return metaBean().metaPropertyMap().keySet();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the periodic frequency of reset dates.
   * <p>
   * Reset dates will be calculated within each accrual period based on unadjusted dates.
   * The frequency must be the same as, or smaller than, the accrual periodic frequency.
   * When calculating the reset dates, the roll convention of the accrual periods will be used.
   * Once the unadjusted date calculation is complete, the business day adjustment specified
   * here will be used.
   * <p>
   * Averaging applies if the reset frequency does not equal the accrual frequency.
   * @return the value of the property, not null
   */
  public Frequency getResetFrequency() {
    return resetFrequency;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the business day adjustment to apply to each reset date.
   * <p>
   * This adjustment is applied to each reset date to ensure it is a valid business day.
   * @return the value of the property, not null
   */
  public BusinessDayAdjustment getResetBusinessDayAdjustment() {
    return resetBusinessDayAdjustment;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the rate averaging method, defaulted to 'Unweighted'.
   * <p>
   * This is used when more than one fixing contributes to the accrual period.
   * <p>
   * Averaging may be weighted by the number of days that the fixing is applicable for.
   * The number of days is based on the reset period, not the period between two fixing dates.
   * <p>
   * Defined by the 2006 ISDA definitions article 6.2a.
   * @return the value of the property, not null
   */
  public RateAveragingMethod getRateAveragingMethod() {
    return rateAveragingMethod;
  }

  //-----------------------------------------------------------------------
  /**
   * Returns a builder that allows this bean to be mutated.
   * @return the mutable builder, not null
   */
  public Builder toBuilder() {
    return new Builder(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      ResetSchedule other = (ResetSchedule) obj;
      return JodaBeanUtils.equal(getResetFrequency(), other.getResetFrequency()) &&
          JodaBeanUtils.equal(getResetBusinessDayAdjustment(), other.getResetBusinessDayAdjustment()) &&
          JodaBeanUtils.equal(getRateAveragingMethod(), other.getRateAveragingMethod());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getResetFrequency());
    hash += hash * 31 + JodaBeanUtils.hashCode(getResetBusinessDayAdjustment());
    hash += hash * 31 + JodaBeanUtils.hashCode(getRateAveragingMethod());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(128);
    buf.append("ResetSchedule{");
    buf.append("resetFrequency").append('=').append(getResetFrequency()).append(',').append(' ');
    buf.append("resetBusinessDayAdjustment").append('=').append(getResetBusinessDayAdjustment()).append(',').append(' ');
    buf.append("rateAveragingMethod").append('=').append(JodaBeanUtils.toString(getRateAveragingMethod()));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code ResetSchedule}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code resetFrequency} property.
     */
    private final MetaProperty<Frequency> resetFrequency = DirectMetaProperty.ofImmutable(
        this, "resetFrequency", ResetSchedule.class, Frequency.class);
    /**
     * The meta-property for the {@code resetBusinessDayAdjustment} property.
     */
    private final MetaProperty<BusinessDayAdjustment> resetBusinessDayAdjustment = DirectMetaProperty.ofImmutable(
        this, "resetBusinessDayAdjustment", ResetSchedule.class, BusinessDayAdjustment.class);
    /**
     * The meta-property for the {@code rateAveragingMethod} property.
     */
    private final MetaProperty<RateAveragingMethod> rateAveragingMethod = DirectMetaProperty.ofImmutable(
        this, "rateAveragingMethod", ResetSchedule.class, RateAveragingMethod.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "resetFrequency",
        "resetBusinessDayAdjustment",
        "rateAveragingMethod");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 101322957:  // resetFrequency
          return resetFrequency;
        case -1777046470:  // resetBusinessDayAdjustment
          return resetBusinessDayAdjustment;
        case 154998811:  // rateAveragingMethod
          return rateAveragingMethod;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public ResetSchedule.Builder builder() {
      return new ResetSchedule.Builder();
    }

    @Override
    public Class<? extends ResetSchedule> beanType() {
      return ResetSchedule.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code resetFrequency} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Frequency> resetFrequency() {
      return resetFrequency;
    }

    /**
     * The meta-property for the {@code resetBusinessDayAdjustment} property.
     * @return the meta-property, not null
     */
    public MetaProperty<BusinessDayAdjustment> resetBusinessDayAdjustment() {
      return resetBusinessDayAdjustment;
    }

    /**
     * The meta-property for the {@code rateAveragingMethod} property.
     * @return the meta-property, not null
     */
    public MetaProperty<RateAveragingMethod> rateAveragingMethod() {
      return rateAveragingMethod;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 101322957:  // resetFrequency
          return ((ResetSchedule) bean).getResetFrequency();
        case -1777046470:  // resetBusinessDayAdjustment
          return ((ResetSchedule) bean).getResetBusinessDayAdjustment();
        case 154998811:  // rateAveragingMethod
          return ((ResetSchedule) bean).getRateAveragingMethod();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      metaProperty(propertyName);
      if (quiet) {
        return;
      }
      throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
    }

  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code ResetSchedule}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<ResetSchedule> {

    private Frequency resetFrequency;
    private BusinessDayAdjustment resetBusinessDayAdjustment;
    private RateAveragingMethod rateAveragingMethod;

    /**
     * Restricted constructor.
     */
    private Builder() {
      applyDefaults(this);
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(ResetSchedule beanToCopy) {
      this.resetFrequency = beanToCopy.getResetFrequency();
      this.resetBusinessDayAdjustment = beanToCopy.getResetBusinessDayAdjustment();
      this.rateAveragingMethod = beanToCopy.getRateAveragingMethod();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 101322957:  // resetFrequency
          return resetFrequency;
        case -1777046470:  // resetBusinessDayAdjustment
          return resetBusinessDayAdjustment;
        case 154998811:  // rateAveragingMethod
          return rateAveragingMethod;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 101322957:  // resetFrequency
          this.resetFrequency = (Frequency) newValue;
          break;
        case -1777046470:  // resetBusinessDayAdjustment
          this.resetBusinessDayAdjustment = (BusinessDayAdjustment) newValue;
          break;
        case 154998811:  // rateAveragingMethod
          this.rateAveragingMethod = (RateAveragingMethod) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public Builder set(MetaProperty<?> property, Object value) {
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setString(String propertyName, String value) {
      setString(meta().metaProperty(propertyName), value);
      return this;
    }

    @Override
    public Builder setString(MetaProperty<?> property, String value) {
      super.setString(property, value);
      return this;
    }

    @Override
    public Builder setAll(Map<String, ? extends Object> propertyValueMap) {
      super.setAll(propertyValueMap);
      return this;
    }

    @Override
    public ResetSchedule build() {
      return new ResetSchedule(
          resetFrequency,
          resetBusinessDayAdjustment,
          rateAveragingMethod);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the {@code resetFrequency} property in the builder.
     * @param resetFrequency  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder resetFrequency(Frequency resetFrequency) {
      JodaBeanUtils.notNull(resetFrequency, "resetFrequency");
      this.resetFrequency = resetFrequency;
      return this;
    }

    /**
     * Sets the {@code resetBusinessDayAdjustment} property in the builder.
     * @param resetBusinessDayAdjustment  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder resetBusinessDayAdjustment(BusinessDayAdjustment resetBusinessDayAdjustment) {
      JodaBeanUtils.notNull(resetBusinessDayAdjustment, "resetBusinessDayAdjustment");
      this.resetBusinessDayAdjustment = resetBusinessDayAdjustment;
      return this;
    }

    /**
     * Sets the {@code rateAveragingMethod} property in the builder.
     * @param rateAveragingMethod  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder rateAveragingMethod(RateAveragingMethod rateAveragingMethod) {
      JodaBeanUtils.notNull(rateAveragingMethod, "rateAveragingMethod");
      this.rateAveragingMethod = rateAveragingMethod;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(128);
      buf.append("ResetSchedule.Builder{");
      buf.append("resetFrequency").append('=').append(JodaBeanUtils.toString(resetFrequency)).append(',').append(' ');
      buf.append("resetBusinessDayAdjustment").append('=').append(JodaBeanUtils.toString(resetBusinessDayAdjustment)).append(',').append(' ');
      buf.append("rateAveragingMethod").append('=').append(JodaBeanUtils.toString(rateAveragingMethod));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
