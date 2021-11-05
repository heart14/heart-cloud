const getToken = () => window.sessionStorage.getItem('token');
const setToken = token => window.sessionStorage.setItem('token', token);
const storage = (key, value) => window.sessionStorage.setItem(key, value);
const removeStorage = key => window.sessionStorage.removeItem(key);


export {
  getToken,
  setToken,
  storage,
  removeStorage,
};
