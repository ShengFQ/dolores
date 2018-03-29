// Generated code from Butter Knife. Do not modify!
package com.dolores.store.ui.main;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class LoginActivity$$ViewBinder<T extends com.dolores.store.ui.main.LoginActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427415, "field 'etMobile'");
    target.etMobile = finder.castView(view, 2131427415, "field 'etMobile'");
    view = finder.findRequiredView(source, 2131427416, "field 'etPassword'");
    target.etPassword = finder.castView(view, 2131427416, "field 'etPassword'");
    view = finder.findRequiredView(source, 2131427417, "method 'onViewClicked'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427418, "method 'onViewClicked'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.etMobile = null;
    target.etPassword = null;
  }
}
