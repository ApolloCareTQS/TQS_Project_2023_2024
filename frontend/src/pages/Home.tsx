import { IonCard, IonCardContent, IonCardHeader, IonCardSubtitle, IonCardTitle, IonCol, IonContent, IonGrid, IonHeader, IonPage, IonRow, IonTitle, IonToolbar } from '@ionic/react';
import ExploreContainer from '../components/ExploreContainer';
import styles from './Home.module.css';
import ScheduleFormContainer from '../components/ScheduleFormContainer';
import AgendaContainer from '../components/AgendaContainer';

const Home: React.FC = () => {
  return (
    <IonPage style={styles}>
      <IonHeader>
        <IonToolbar>
          <IonTitle>Home</IonTitle>
        </IonToolbar>
      </IonHeader>
      <IonContent>
        <IonGrid>
          <IonRow>
            <IonCol size='auto'>
              <IonCard>
                <IonCardHeader>
                  <IonCardSubtitle>
                    Member
                  </IonCardSubtitle>
                  <IonCardTitle>
                    Hello, User!
                  </IonCardTitle>
                </IonCardHeader>
                <IonCardContent>
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
                </IonCardContent>
              </IonCard>
            </IonCol>
            <IonCol>
              <AgendaContainer />
            </IonCol>
          </IonRow>
          <IonRow>
            <IonCol>
              <ScheduleFormContainer />
            </IonCol>
            <IonCol>
              <ScheduleFormContainer />
            </IonCol>
          </IonRow>
        </IonGrid>
      </IonContent>
    </IonPage>
  );
};

export default Home;
