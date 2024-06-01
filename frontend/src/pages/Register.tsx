import ExploreContainer from '../components/ExploreContainer';
import { IonButton, IonButtons, IonCard, IonCardContent, IonCardHeader, IonCardSubtitle, IonCardTitle, IonCol, IonContent, IonGrid, IonHeader, IonIcon, IonInput, IonItem, IonLabel, IonList, IonMenu, IonMenuButton, IonPage, IonRow, IonTitle, IonToolbar } from '@ionic/react';
import styles from './Register.module.css';
import { useContext, useState } from 'react';
import { backendURI } from '../App';

const Register: React.FC = () => {
    const role="PATIENT";
    const [email,setEmail]=useState(false);
    const [password,setPassword]=useState(false);
    const [username,setUsername]=useState(false);

    const handleRegister = async() => {
        const baseUrl= backendURI;
        const body={
            role:role,
            email:email,
            username:username,
            password:password
        }
        console.debug(`registering with data ${body}`);

        const response=await fetch(baseUrl+"/auth/v1/register", { 
            method:"POST",
            body: JSON.stringify(body),
            headers: {"Content-Type":"application/json"}
        });

        switch(response.status){
            case 200:
                console.debug(`register successful, body: ${await response.json()}`);
                window.location.href="/";
                break;
            //may add specific responses in the future
            default:
                console.warn(`register failed! error code: ${response.status}, error text: ${await response.json()}`);
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
                            <IonLabel><h2>Register</h2></IonLabel>
                        </IonCardHeader>
                        <IonCardContent>
                            <IonList>
                                <IonItem>
                                    <IonInput labelPlacement="stacked" label="Email" placeholder="example@gmail.com" onIonInput={(e:any)=> setEmail(e.target.value)}></IonInput>
                                </IonItem>
                                <IonItem>
                                    <IonInput labelPlacement="stacked" label="Name" placeholder="Bob Alice" onIonInput={(e:any)=>setUsername(e.target.value)}></IonInput>
                                </IonItem>
                                <IonItem>
                                    <IonInput type="password" labelPlacement="stacked" label="Password" onIonInput={(e:any)=>setPassword(e.target.value)}></IonInput>
                                </IonItem>
                            </IonList>
                            <IonButton onClick={handleRegister}>Register</IonButton>
                        </IonCardContent>
                    </IonCard>
                </div>
            </IonContent>
        </IonPage>
    );
};

export default Register;