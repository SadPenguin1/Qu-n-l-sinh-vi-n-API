import { BrowserRouter, Route, Router ,Routes,Link,Outlet} from "react-router-dom";
import { ProvideAuth } from "./context/ProvideAuth";
import Login from './page/login/Login';
import Signup from './page/login/SignUp';
import { User } from "./page/login/User";
import { Student } from "./page/login/Student";
import { Course } from "./page/login/Course";
import { Score } from "./page/login/Score";
import { AppBar, Box, Button, Container ,Divider,Grid, IconButton, List, ListItem, ListItemButton, ListItemIcon, ListItemText, Toolbar, Typography} from "@mui/material";
import { Accessible, CollectionsBookmark, Inbox, MenuBook, Person, Scoreboard } from "@mui/icons-material";
import { Role } from "./page/login/Role";
import { UserSearchRedux } from "./page/login/UserRedux/UserSearchRedux";

import { Provider } from "react-redux";
import { store } from "./redux/store";
import UserCreate from "./page/login/UserRedux/UserCreate";
import { StudentSearchRedux } from "./page/login/StudentRedux/StudentSearchRedux";
import { RoleSearchRedux } from "./page/login/RoleRedux/RoleSearchRedux";
import { CourseSearchRedux } from "./page/login/CourseRedux/CourseSearchRedux";


function App() {
  return (
    //<UserSearch/>
    <Provider store={store}>
    <ProvideAuth>
      <BrowserRouter>
      <Routes>
          <Route path="/" element={<Login/>} />
          <Route path="/signup" element={<Signup/>} />

          <Route path='dashboard' element={<DashboardLayout/>} >
            <Route path='user' element={<UserSearchRedux/>} />
            <Route path='user/add' element={<UserCreate/>} />
           
            <Route path='role' element={<RoleSearchRedux/>} />
            <Route path='student' element={<StudentSearchRedux/>} />
            <Route path='course' element={<CourseSearchRedux/>} />
            <Route path='score' element={<Score/>} />
          </Route>

        </Routes>
      </BrowserRouter>
      </ProvideAuth>
      </Provider>
  );
}

export default App;

function DashboardLayout() {
  return (
 
      <Grid container spacing={0.5}>
        <Grid item xs={2} md={12}>
          <ButtonAppBar/>
        </Grid>
        <Grid item xs={2} md={2}>
          <Menu />
        </Grid>
        <Grid item xs={2} md={10}>
          <Outlet />
        </Grid>
      </Grid>
  
  );
}
//
function Menu() {
  return (
    <Box sx={{ width: '100%', maxWidth: 360, bgcolor: 'background.paper' }}>
    <nav aria-label="main mailbox folders">
      <List>
        <ListItem disablePadding>
          <ListItemButton>
            <ListItemIcon>
              <Inbox/>
            </ListItemIcon>
          <Link to={"/"} underline="hover">Login</Link>
          </ListItemButton>
        </ListItem>

        <ListItem disablePadding>
          <ListItemButton>  
          <ListItemIcon>
              <Person/>
            </ListItemIcon>
          <Link to={"/dashboard/user"} underline="hover">{'User'}</Link>
          </ListItemButton>
        </ListItem>

        <ListItem disablePadding>
          <ListItemButton>  
          <ListItemIcon>
              <Person/>
            </ListItemIcon>
          <Link to={"/dashboard/role"} underline="hover">{'Role'}</Link>
          </ListItemButton>
        </ListItem>

        <ListItem disablePadding>
          <ListItemButton> 
          <ListItemIcon>
              <Accessible/>
            </ListItemIcon> 
          <Link to = "/dashboard/student" underline="hover" >Student</Link>
          </ListItemButton>
        </ListItem>

        <ListItem disablePadding>
          <ListItemButton>  
          <ListItemIcon>
              <CollectionsBookmark/>
            </ListItemIcon>
          <Link to={"/dashboard/course"} underline="hover">Course</Link>
          </ListItemButton>
        </ListItem>

        <ListItem disablePadding>
          <ListItemButton>  
          <ListItemIcon>
              <Scoreboard/>
            </ListItemIcon> 
          <Link to={"/dashboard/score"} underline="hover">Score</Link>
          </ListItemButton>
        </ListItem>

      </List>
    </nav>
    <Divider />
  </Box>
   );
  }

   function ButtonAppBar() {
    return (
      <Box sx={{ flexGrow: 1 }}>
        <AppBar position="static">
          <Toolbar>
            <IconButton
              size="large"
              edge="start"
              color="inherit"
              aria-label="menu"
              sx={{ mr: 2 }}
            >
              <MenuBook/>
            </IconButton>
            <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
              STUDENT MANAGER
            </Typography>
            <Link to="/signup" style={{textDecoration:'none'}}>
            <Button  sx={{marginTop:3,borderRadius:3}}>Change to SignUp</Button>
            </Link>
          </Toolbar>
        </AppBar>
      </Box>
    );
  }


