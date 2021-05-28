import React,{useState} from 'react';
import { PageHeader } from './global-components/page-header';
import { FooterV1 } from './global-components/footer';
import { Navbar } from './global-components/navbar';
import { Box, Paper, Tab, Tabs, Typography } from '@material-ui/core';
import { LoginView } from './section-components/LoginView';
import { RegisterView } from './section-components/RegisterView';


export const Login = () =>{


    const [value, setValue] = useState(0);

    const handleChange = (event,newValue) =>{
        setValue(newValue);
    };

    const paperStyle = {width:310,margin:"20px auto"};


    function TabPanel(props) {
        const { children, value, index, ...other } = props;

        return (
          <div
            role="tabpanel"
            hidden={value !== index}
            id={`simple-tabpanel-${index}`}
            aria-labelledby={`simple-tab-${index}`}
            {...other}
          >
            {value === index && (
              <Box >
                <Typography>{children}</Typography>
              </Box>
            )}
          </div>
        );
      }

    return (
        <div>
        <Navbar />
        <PageHeader HeaderTitle={"Welcome to Envirify"}/>
        <Paper elevation={20} style={paperStyle}>
            <Tabs
                value={value}
                indicatorColor="primary"
                textColor="primary"
                onChange={handleChange}
                aria-label="disabled tabs example"
            >
                <Tab label="Sign in" />
                <Tab label="Sign up" />
            </Tabs>
            <TabPanel value={value} index={0}>
                <LoginView handleChange={handleChange}></LoginView>
            </TabPanel>
            <TabPanel value={value} index={1}>
                <RegisterView></RegisterView>
            </TabPanel>
        </Paper>
        <FooterV1 />
        </div>
    );
}
