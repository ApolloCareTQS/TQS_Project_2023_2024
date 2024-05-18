import ExploreContainer from '../components/ExploreContainer';
import { IonButton, IonButtons, IonCard, IonCardContent, IonCardHeader, IonCardSubtitle, IonCardTitle, IonCol, IonContent, IonGrid, IonHeader, IonIcon, IonInput, IonItem, IonLabel, IonList, IonMenu, IonMenuButton, IonPage, IonRow, IonTitle, IonToolbar } from '@ionic/react';
import styles from './Register.module.css';

const Register: React.FC = () => {
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
                                    <IonInput labelPlacement="stacked" label="Email" placeholder="example@gmail.com"></IonInput>
                                </IonItem>
                                <IonItem>
                                    <IonInput labelPlacement="stacked" label="Name" placeholder="Bob Alice"></IonInput>
                                </IonItem>
                                <IonItem>
                                    <IonInput label="Telephone" labelPlacement="stacked" type="tel" placeholder="888-888-8888"></IonInput>
                                </IonItem>
                                <IonItem>
                                    <IonInput type="password" labelPlacement="stacked" label="Password" ></IonInput>
                                </IonItem>
                            </IonList>
                            <IonButton>Register</IonButton>
                        </IonCardContent>
                    </IonCard>
                </div>
            </IonContent>
        </IonPage>
    );
};

export default Register;