import ExploreContainer from '../components/ExploreContainer';
import { IonButton, IonButtons, IonCard, IonCardContent, IonCardHeader, IonCardSubtitle, IonCardTitle, IonCol, IonContent, IonGrid, IonHeader, IonIcon, IonInput, IonItem, IonLabel, IonList, IonMenu, IonMenuButton, IonPage, IonRow, IonTitle, IonToolbar } from '@ionic/react';
import styles from './Login.module.css';
import { useContext, useState } from 'react';
import { AuthContext, User, backendURI } from '../App';
import axios from 'axios';

//axios.defaults.baseURL = backendURI;

const Login: React.FC = () => {
    const role="PATIENT";
    const [email,setEmail]=useState(false);
    const [password,setPassword]=useState(false);

    const { login } = useContext(AuthContext);

    const handleLogin = async() => {
        const body={
            role:role,
            email:email,
            password:password
        }
        console.debug(`logging in with data ${body}`);

        const response = await axios.post('/auth/v1/login', body, {withCredentials: true}).then(function (response) {
            const user: User = {
                id: response.data.id,
                username: response.data.name,
                signedIn: true,
            };
            login(user);
            window.location.href = '/';
            console.log(response);
        });
    }
    
    return (
        <IonPage>
            <IonHeader>
                <IonToolbar>
                    <IonTitle color='primary'>ApolloCare</IonTitle>
                </IonToolbar>
            </IonHeader>
            <IonContent>
                <div className={styles.card_container}>
                    <IonCard>
                        <IonCardHeader>
                            <IonLabel><h2>Login</h2></IonLabel>
                        </IonCardHeader>
                        <IonCardContent>
                            <IonList>
                                <IonItem>
                                    <IonInput labelPlacement="stacked" label="Email" placeholder="example@gmail.com" onIonInput={(e:any)=> setEmail(e.target.value)}></IonInput>
                                </IonItem>
                                <IonItem>
                                    <IonInput type="password" labelPlacement="stacked" label="Password" onIonInput={(e:any)=> setPassword(e.target.value)}></IonInput>
                                </IonItem>
                            </IonList>
                            <IonButton onClick={handleLogin}>Login</IonButton>
                        </IonCardContent>
                    </IonCard>
                </div>
            </IonContent>
        </IonPage>
    );
};

export default Login;