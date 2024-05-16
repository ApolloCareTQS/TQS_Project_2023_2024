import ExploreContainer from '../components/ExploreContainer';
import { IonButton, IonButtons, IonCard, IonCardContent, IonCardHeader, IonCardSubtitle, IonCardTitle, IonCol, IonContent, IonGrid, IonHeader, IonIcon, IonInput, IonItem, IonLabel, IonList, IonMenu, IonMenuButton, IonPage, IonRow, IonTitle, IonToolbar } from '@ionic/react';
import styles from './Login.module.css';

const Login: React.FC = () => {
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
                                    <IonInput labelPlacement="stacked" label="Email" placeholder="example@gmail.com"></IonInput>
                                </IonItem>
                                <IonItem>
                                    <IonInput type="password" labelPlacement="stacked" label="Password" ></IonInput>
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