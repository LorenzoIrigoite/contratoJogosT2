import '@vaadin/tooltip/src/vaadin-tooltip.js';
import '@vaadin/polymer-legacy-adapter/style-modules.js';
import '@vaadin/text-field/src/vaadin-text-field.js';
import '@vaadin/horizontal-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/button/src/vaadin-button.js';
import 'Frontend/generated/jar-resources/buttonFunctions.js';
import '@vaadin/vertical-layout/src/vaadin-vertical-layout.js';
import '@vaadin/common-frontend/ConnectionIndicator.js';
import '@vaadin/vaadin-lumo-styles/color-global.js';
import '@vaadin/vaadin-lumo-styles/typography-global.js';
import '@vaadin/vaadin-lumo-styles/sizing.js';
import '@vaadin/vaadin-lumo-styles/spacing.js';
import '@vaadin/vaadin-lumo-styles/style.js';
import '@vaadin/vaadin-lumo-styles/vaadin-iconset.js';

const loadOnDemand = (key) => {
  const pending = [];
  if (key === '13e3b5fe3749e5185e3e29694641995f4a5e48e7545c90fa529dc08f9f8b47db') {
    pending.push(import('./chunks/chunk-94772a40b9db1653036fcfdd3ca79933d32c2df615abac0db5eac7ed55f562d7.js'));
  }
  if (key === '90f6957fdfddd930a0a70ba91a1fee7ed8366a6a8d6a8efd85bc00bef2fef985') {
    pending.push(import('./chunks/chunk-1d5f230aeee932cb1909c5f6d360a0f1d7e858872cd1370235343f3851363284.js'));
  }
  return Promise.all(pending);
}

window.Vaadin = window.Vaadin || {};
window.Vaadin.Flow = window.Vaadin.Flow || {};
window.Vaadin.Flow.loadOnDemand = loadOnDemand;
window.Vaadin.Flow.resetFocus = () => {
 let ae=document.activeElement;
 while(ae&&ae.shadowRoot) ae = ae.shadowRoot.activeElement;
 return !ae || ae.blur() || ae.focus() || true;
}