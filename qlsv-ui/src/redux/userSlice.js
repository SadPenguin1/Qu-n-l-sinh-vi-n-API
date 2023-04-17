import { createSlice } from "@reduxjs/toolkit";
import { searchUserAPI } from "../service/user.service";

 const userSlice = createSlice({
    name: "user",
    initialState: {
        isLoading: false,
        error: null,
        users:[],
        user: null,
        search:{
            value: "%%",
            page: 0,
            size: 10
        }
        
    },
    reducers: {
        startLoading: (state) => {
            state.isLoading = true;
        },
        setError: (state,action)=>{
            state.isLoading =false
            state.error = action.payload
        },
        setUsers: (state,action)=> {
            console.log(action.payload.data);
            state.users = action.payload.data
            state.isLoading = false;

        },
        setUserSearch: (state, action) => {
            state.search = action.payload;//doc gia tri truyen vao
            state.isLoading = false;
        },
        setUser: (state,action)=> {
            state.user = action.payload.data
            state.isLoading = false
            state.error =null
        },
    }
});

export const {startLoading,setUserSearch} = userSlice.actions;

export default userSlice.reducer;

export const searchUser =()=> async(dispatch,getState) => {

    dispatch(userSlice.actions.startLoading())

    const {user} = getState()
    const {search} = user

    const resp = await searchUserAPI(search)
   // console.log(resp.code)

    if (resp.code === '200')
        dispatch(userSlice.actions.setUsers(resp))
     else
        dispatch(userSlice.actions.setError(resp.code))    
    
}