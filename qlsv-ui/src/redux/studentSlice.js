import { createSlice } from "@reduxjs/toolkit";
import { searchStudentAPI } from "../service/student.service";

 const studentSlice = createSlice({
    name: "student",
    initialState: {
        isLoading: false,
        error: null,
        students:[],
        student: null,
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
        setStudents: (state,action)=> {
            console.log(action.payload.data);
            state.students = action.payload.data
            state.isLoading = false;

        },
        setStudentSearch: (state, action) => {
            state.search = action.payload;//doc gia tri truyen vao
            state.isLoading = false;
        },
        setStudent: (state,action)=> {
            state.student = action.payload.data
            state.isLoading = false
            state.error =null
        },
    }
});

export const {startLoading,setStudentSearch} = studentSlice.actions;

export default studentSlice.reducer;

export const searchStudent =()=> async(dispatch,getState) => {

    dispatch(studentSlice.actions.startLoading())

    const {student} = getState()
    const {search} = student

    const resp = await searchStudentAPI(search)
   // console.log(resp.code)

    if (resp.code === '200')
        dispatch(studentSlice.actions.setStudents(resp))
     else
        dispatch(studentSlice.actions.setError(resp.code))    
    
}