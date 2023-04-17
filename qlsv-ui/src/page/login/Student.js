import React, { useEffect, useState } from "react";
import { searchStudentAPI,createStudentAPI,updateStudentAPI,deleteStudentAPI } from "../../service/student.service";
import { Delete, Send, Update } from "@mui/icons-material";
import { Box, Button, FormControl, InputLabel, MenuItem, Select, Table, TableBody, TableCell, TableHead, TableRow, TextField } from "@mui/material";


export function Student() {
    
    let [studentArr, setStudentArr] = useState([])
    let [data,setData] = useState({
        "value": "%%",
        "page": 0,
        "size": 10
    })
    useEffect(() => {
        const readData = async () => {
            // async request.
            const response = await searchStudentAPI(data);
            setStudentArr(response.data)
            if (response) {
                return;
            }
        }
        readData();
 
    }, [data])

    let handleTextChange = (e)=>{
        setData({...data,[e.target.name]:e.target.value})
    }

    let [studentDTO, setStudentDTO] = useState({
        id: "",
        studentCode: "dung",
       
      });

      let handleChangeStudent = (e) => {
        setStudentDTO({ ...studentDTO, [e.target.name]: e.target.value });

      };
    
    let createStudent = async () => {
        try {
          console.log(studentDTO);
          let resp = await createStudentAPI(studentDTO); 
          console.log("Tạo student thành công");
        } catch (err) {
          console.log(err);
        }
      };

      let updateStudent = async () => {
        try {
          console.log(studentDTO);
          let resp = await updateStudentAPI(studentDTO); 
          console.log("Update data: ", resp.data);
          console.log("Update student thành công");
          setStudentArr(resp.data.data);
        } catch (err) {
          console.log(err);
        }
      };

      let deleteStudent = async (id) => {
        try {
          let yes = window.confirm("Are you sure want to delete this item ?");
          let resp = await deleteStudentAPI(id); 
          let newArray = studentArr.filter(function (item) {
          
            return item.id !== id;
          });
          console.log("Delete student thành công");
          setStudentArr(newArray.data.data);
          
        } catch (err) {
          console.log(err);
        }
      }

    return (
        <div>
            
      <th>Create Student</th>
      <form>
        
        <TextField  label="StudentCode" name="studentCode" onChange={handleChangeStudent} variant="standard" placeholder="studentCode.."/>
        <Button  onClick={createStudent}  startIcon={<Send/>} variant="outlined">Save </Button>
        
    
        <Box
            component="form"
            sx={{'& .MuiTextField-root': { m: 1, width: '25ch' }, }}
            noValidate
            autoComplete="off"
                 >
            <TextField  label="Search" name ="value" onChange={handleTextChange}  variant="standard" placeholder="Search..."></TextField>
          
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
                    <TableCell align="center">StudentCode</TableCell>
                    <TableCell align="left">Option</TableCell>
                </TableRow>
                </TableHead>
                <TableBody>
                {
                    studentArr.map(item => {
                        return (
                            <TableRow key={item.id}>
                                <TableCell>{item.id}</TableCell>
                                <TableCell>{item.studentCode}</TableCell>                                                                           
                                <TableCell>
                                  <Button  onClick={() => deleteStudent(item.id)} startIcon={<Delete/>} variant="outlined">Delete </Button>
                                  <Button  onClick={() => updateStudent(item.id)} startIcon={<Update/>} variant="outlined">Update</Button>
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