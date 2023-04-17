import React, { useEffect} from "react";
import { deleteUserAPI } from "../../../service/user.service";
import { useDispatch, useSelector } from "react-redux";
import { searchUser, setUserSearch} from "../../../redux/userSlice";
import { Box, Button, FormControl, InputLabel, MenuItem, Select, Table, TableBody, TableCell, TableHead, TableRow, TextField } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { Delete, Send } from "@mui/icons-material";

export function UserSearchRedux() {

    let navigate = useNavigate()
  //  const{showError} = useError()

    const {users,search} = useSelector(s => s.user)
    console.log(users);
    const dispatch = useDispatch();

   // let{search} = users

    useEffect(() => {
        const timeout = setTimeout(()=>{
            find()
        },500)
        return () => clearTimeout(timeout)
    },[search])

    const find = async () => {
        dispatch(searchUser())
    }

    const handleTextChange = (e) => dispatch(setUserSearch({...search,[e.target.name]: e.target.value}))

    const deleteUser = async (id) => {
        let{code} = await deleteUserAPI(id)

        if(code === 200){
            find()
        }else{
           console.log(code)
        }
    }
    const a = async () => {
        navigate("/dashboard/user/add")
    }

    return (
        <div>
            
      <form>
      <Button  onClick={a} startIcon={<Send/>} variant="outlined">Create User </Button>
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
                    <TableCell align="center">Username</TableCell>
                    <TableCell align="center">Password</TableCell>
                    <TableCell align="center">Enable</TableCell>
                    <TableCell align="center">Email</TableCell>
                  
                    <TableCell align="center">Option</TableCell>
                </TableRow>
                </TableHead>
                <TableBody>
                {
                    (users).map(item => {
                        return (
                            <TableRow key={item.id}>
                                <TableCell>{item.id}</TableCell>
                                <TableCell>{item.username}</TableCell>
                                <TableCell>{item.password}</TableCell>
                                <TableCell>{item.enable}</TableCell>  
                                <TableCell>{item.email}</TableCell> 
                                                    

                                
                                <TableCell>
                                  <Button  onClick={() => deleteUser(item.id)} startIcon={<Delete/>} variant="outlined">Delete </Button>
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