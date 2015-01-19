/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.platform.pricer.impl.swap;

import java.util.function.ToDoubleBiFunction;

import com.opengamma.collect.ArgChecker;
import com.opengamma.platform.finance.swap.ExpandedSwapLeg;
import com.opengamma.platform.finance.swap.PaymentEvent;
import com.opengamma.platform.finance.swap.PaymentPeriod;
import com.opengamma.platform.pricer.PricingEnvironment;
import com.opengamma.platform.pricer.swap.PaymentEventPricerFn;
import com.opengamma.platform.pricer.swap.PaymentPeriodPricerFn;
import com.opengamma.platform.pricer.swap.SwapLegPricerFn;

/**
 * Pricer implementation for expanded swap legs.
 * <p>
 * The swap leg is priced by examining the periods and events.
 */
public class DefaultExpandedSwapLegPricerFn
    implements SwapLegPricerFn<ExpandedSwapLeg> {

  /**
   * Default implementation.
   */
  public static final DefaultExpandedSwapLegPricerFn DEFAULT = new DefaultExpandedSwapLegPricerFn(
      DispatchingPaymentPeriodPricerFn.DEFAULT,
      DispatchingPaymentEventPricerFn.DEFAULT);

  /**
   * Pricer for {@link PaymentPeriod}.
   */
  private final PaymentPeriodPricerFn<PaymentPeriod> paymentPeriodPricerFn;
  /**
   * Pricer for {@link PaymentEvent}.
   */
  private final PaymentEventPricerFn<PaymentEvent> paymentEventPricerFn;

  /**
   * Creates an instance.
   * 
   * @param paymentPeriodPricerFn  the pricer for {@link PaymentPeriod}
   * @param paymentEventPricerFn  the pricer for {@link PaymentEvent}
   */
  public DefaultExpandedSwapLegPricerFn(
      PaymentPeriodPricerFn<PaymentPeriod> paymentPeriodPricerFn,
      PaymentEventPricerFn<PaymentEvent> paymentEventPricerFn) {
    this.paymentPeriodPricerFn = ArgChecker.notNull(paymentPeriodPricerFn, "paymentPeriodPricerFn");
    this.paymentEventPricerFn = ArgChecker.notNull(paymentEventPricerFn, "paymentEventPricerFn");
  }

  //-------------------------------------------------------------------------
  @Override
  public double presentValue(PricingEnvironment env, ExpandedSwapLeg swapLeg) {
    return value(env, swapLeg, paymentPeriodPricerFn::presentValue, paymentEventPricerFn::presentValue);
  }

  @Override
  public double futureValue(PricingEnvironment env, ExpandedSwapLeg swapLeg) {
    return value(env, swapLeg, paymentPeriodPricerFn::futureValue, paymentEventPricerFn::futureValue);
  }

  // calculate present or future value
  private static double value(
      PricingEnvironment env,
      ExpandedSwapLeg swapLeg,
      ToDoubleBiFunction<PricingEnvironment, PaymentPeriod> periodFn,
      ToDoubleBiFunction<PricingEnvironment, PaymentEvent> eventFn) {
    double valuePeriods = swapLeg.getPaymentPeriods().stream()
        .mapToDouble(p -> periodFn.applyAsDouble(env, p))
        .sum();
    double valueEvents = swapLeg.getPaymentEvents().stream()
      .mapToDouble(e -> eventFn.applyAsDouble(env, e))
      .sum();
     return valuePeriods + valueEvents;
  }

}