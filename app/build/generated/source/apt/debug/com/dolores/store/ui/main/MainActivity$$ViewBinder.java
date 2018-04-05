// Generated code from Butter Knife. Do not modify!
package com.dolores.store.ui.main;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainActivity$$ViewBinder<T extends com.dolores.store.ui.main.MainActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427422, "field 'dingLayout' and method 'onClick'");
    target.dingLayout = finder.castView(view, 2131427422, "field 'dingLayout'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427423, "field 'bookLayout' and method 'onClick'");
    target.bookLayout = finder.castView(view, 2131427423, "field 'bookLayout'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427424, "field 'mineLayout' and method 'onClick'");
    target.mineLayout = finder.castView(view, 2131427424, "field 'mineLayout'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.dingLayout = null;
    target.bookLayout = null;
    target.mineLayout = null;
  }
}
