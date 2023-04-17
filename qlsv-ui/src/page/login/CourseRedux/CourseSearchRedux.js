import React, { useEffect} from "react";
import { useDispatch, useSelector } from "react-redux";
import { Box, Button, FormControl, InputLabel, MenuItem, Select, Table, TableBody, TableCell, TableHead, TableRow, TextField } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { Delete, Send } from "@mui/icons-material";
import { deleteCourseAPI } from "../../../service/course.service";
import { searchCourse, setCourseSearch } from "../../../redux/courseSlice";

export function CourseSearchRedux() {

    let navigate = useNavigate()
  //  const{showError} = useError()

    const {courses,search} = useSelector(s => s.course)
    console.log(courses);
    const dispatch = useDispatch();

   // let{search} = users

    useEffect(() => {
        const timeout = setTimeout(()=>{
            find()
        },500)
        return () => clearTimeout(timeout)
    },[search])

    const find = async () => {
        dispatch(searchCourse())
    }

    const handleTextChange = (e) => dispatch(setCourseSearch({...search,[e.target.name]: e.target.value}))

    const deleteCourse = async (id) => {
        let{code} = await deleteCourseAPI(id)

        if(code === 200){
            find()
        }else{
           console.log(code)
        }
    }
    const a = async () => {
        navigate("/dashboard/course/add")
    }

    return (
        <div>
            
      <form>
      <Button  onClick={a} startIcon={<Send/>} variant="outlined">Create Course </Button>
      <Box
            component="form"
            sx={{'& .MuiTextField-root': { m: 1, width: '25ch' }, }}
            noValidate
            autoComplete="off"
                 >
            <TextField label="Search" name ="value" onChange={handleTextChange}  variant="standard" placeholder="Search..."></TextField>
          
            <FormControl>
               <InputLabel id="demo-simple-select-label">Size</InputLabel>
                <Select name ="size" onChange={handleTextChange}>
              
                <MenuItem value="10">10</MenuItem>
                <MenuItem value="20">20</MenuItem>
            </Select>
            </FormControl>
            </Box>
            <Table  sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
                <TableHead>
                <TableRow>
                    <TableCell>ID</TableCell>
                    <TableCell align="center">Course</TableCell>
                   
                  
                    <TableCell align="center">Option</TableCell>
                </TableRow>
                </TableHead>
                <TableBody>
                {
                    (courses).map(item => {
                        return (
                            <TableRow key={item.id}>
                                <TableCell>{item.id}</TableCell>
                                <TableCell>{item.name}</TableCell>
                             

                                
                                <TableCell>
                                  <Button  onClick={() => deleteCourse(item.id)} startIcon={<Delete/>} variant="outlined">Delete </Button>
                                  {/* <Button  onClick={() => updateUser(item.id)} startIcon={<Update/>} variant="outlined">Update</Button> */}
                               </TableCell>
                            </TableRow>
                        )
                    })
                }
             </TableBody>   
            </Table>
           </form>
         </div>
    )
 }