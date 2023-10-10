import { Module } from 'vuex';

export const translationStore: Module<any, any> = {
  state: {
    currentLanguage: localStorage.getItem('currentLanguage') || 'fr',
    languages: {
      bg: { name: 'Български' },
      'zh-cn': { name: '中文（简体）' },
      en: { name: 'English' },
      fr: { name: 'Français' },
      de: { name: 'Deutsch' },
      el: { name: 'Ελληνικά' },
      in: { name: 'Bahasa Indonesia' },
      it: { name: 'Italiano' },
      'pt-br': { name: 'Português (Brasil)' },
      'pt-pt': { name: 'Português' },
      ru: { name: 'Русский' },
      es: { name: 'Español' },
      sv: { name: 'Svenska' },
      tr: { name: 'Türkçe' },
      ta: { name: 'தமிழ்' },
      th: { name: 'ไทย' },
      vi: { name: 'Tiếng Việt' },
      // jhipster-needle-i18n-language-key-pipe - JHipster will add/remove languages in this object
    },
  },
  getters: {
    currentLanguage: state => state.currentLanguage,
    languages: state => state.languages,
  },
  mutations: {
    currentLanguage(state, newLanguage) {
      state.currentLanguage = newLanguage;
      localStorage.setItem('currentLanguage', newLanguage);
    },
  },
};
