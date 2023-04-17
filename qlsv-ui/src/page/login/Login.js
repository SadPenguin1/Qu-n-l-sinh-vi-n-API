import { Button, TextField, Typography } from "@mui/material";
import { Box } from "@mui/system";
import React, { useState } from "react";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { loginAPI } from '../../service/auth.service';
import { useAuth } from '../../hooks/useAuth'
function Login() {
    const navigate = useNavigate();

    const { setAuth } = useAuth();
    const [username,setUserName]=useState("");
    const [password,setPassWord]=useState("");

    const handleLogin = async()=>{
      
        const resp = await loginAPI({username:username,password:password});
        if(resp.code==='200'){
         // console.log(JSON.stringify(resp.data));
            setAuth(resp.data)
            navigate("/dashboard")
          
          //  localStorage.setItem("token",resp.data)
        }else{
            alert("Error")
        }
         
    }
  return (
    <div >
      <form>
        <Box sx={{display:'flex',justifyContent:"center",alignItems:'center',flexDirection:'column',margin:'auto',marginTop:5,padding:3,borderRadius:5,boxShadow:'5px 5px 10px #ccc',":hover":{boxShadow:'10px 10px 20px #ccc'}}} maxWidth={400}>
        <Typography variant="h2" padding={3} textAlign="center">Login</Typography>
            <TextField margin="normal" type={'email'} variant="outlined" placeholder="Username" onChange={(e)=>setUserName(e.target.value)}></TextField>
            <TextField margin="normal" type={'password'} variant="outlined" placeholder="Password" onChange={(e)=>setPassWord(e.target.value)}></TextField>
            <Button variant="contained" color="warning" sx={{marginTop:3,borderRadius:3}} onClick={()=>handleLogin()}>Login</Button>
           <Link to="/signup" style={{textDecoration:'none'}}>
            <Button  sx={{marginTop:3,borderRadius:3}}>Change to SignUp</Button>
            </Link>
        </Box>
      </form>
    </div>
  );
}

export default Login;