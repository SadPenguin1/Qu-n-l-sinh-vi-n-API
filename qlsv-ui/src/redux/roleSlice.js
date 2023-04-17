import { createSlice } from "@reduxjs/toolkit";
import { searchRoleAPI } from "../service/role.service";

 const roleSlice = createSlice({
    name: "role",
    initialState: {
        isLoading: false,
        error: null,
        roles:[],
        role: null,
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
        setRoles: (state,action)=> {
            console.log(action.payload.data);
            state.roles = action.payload.data
            state.isLoading = false;

        },
        setRoleSearch: (state, action) => {
            state.search = action.payload;//doc gia tri truyen vao
            state.isLoading = false;
        },
        setRole: (state,action)=> {
            state.role = action.payload.data
            state.isLoading = false
            state.error =null
        },
    }
});

export const {startLoading,setRoleSearch} = roleSlice.actions;

export default roleSlice.reducer;

export const searchRole =()=> async(dispatch,getState) => {

    dispatch(roleSlice.actions.startLoading())

    const {role} = getState()
    const {search} = role

    const resp = await searchRoleAPI(search)
   // console.log(resp.code)

    if (resp.code === '200')
        dispatch(roleSlice.actions.setRoles(resp))
     else
        dispatch(roleSlice.actions.setError(resp.code))    
    
}