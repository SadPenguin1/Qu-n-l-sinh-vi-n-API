import { createContext, useState } from "react";
export const AuthContext = createContext();
export const getSession = () => {
    return JSON.parse(localStorage.getItem('session'));
    };
   
    
export const setSessionInLocalStorage = (token) => {
    localStorage.setItem('session', JSON.stringify(token))
    return true
    };
export const ProvideAuth = ({ children }) => {

const auth = getSession();
const [session, setSession] = useState(auth || '');
const setAuth = (token) => {
setSession(token);
setSessionInLocalStorage(token);
};
const { user, token } = session;
return (
<AuthContext.Provider value={{ user, token, setAuth }}>
{children}
</AuthContext.Provider>
)
};