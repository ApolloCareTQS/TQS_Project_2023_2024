import ExploreContainer from '../components/ExploreContainer';
import { IonButton, IonButtons, IonCard, IonCardContent, IonCardHeader, IonCardSubtitle, IonCardTitle, IonCol, IonContent, IonGrid, IonHeader, IonIcon, IonInput, IonItem, IonLabel, IonList, IonMenu, IonMenuButton, IonPage, IonRow, IonTitle, IonToolbar } from '@ionic/react';
import styles from './Login.module.css';

const Login: React.FC = () => {
    const role="PATIENT";
    const [email,setEmail]=useState(false);
    const [password,setPassword]=useState(false);

    const handleRegister = async() => {
        const baseUrl="localhost:8080"; // when deploying we'll likely use docker compose so we'll have to rename this to the server container's name
        const body={
            role:role,
            email:email,
            password:password
        }
        console.debug(`logging in with data ${body}`);

        const response=await fetch(baseUrl+"auth/v1/login", { 
            method:"POST",
            body: JSON.stringify(body),
            headers: {"Content-Type":"application/json"}
        });

        switch(response.status){
            case 200:
                console.debug(`login successful, body: ${await response.json()}`);
                window.location.href="/";
                break;
            //may add specific responses in the future
            default:
                console.warn(`login failed! error code: ${response.status}, error text: ${await response.json()}`);
                alert("An unexpected error occurred");
        }
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
                            <IonButton>Login</IonButton>
                        </IonCardContent>
                    </IonCard>
                </div>
            </IonContent>
        </IonPage>
    );
};

export default Login;