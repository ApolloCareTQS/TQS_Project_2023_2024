import { IonContent, IonHeader, IonPage, IonTitle, IonToolbar } from '@ionic/react';
import ExploreContainer from '../components/ExploreContainer';
import styles from './Schedule.module.css';
import ScheduleFormContainer from '../components/ScheduleFormContainer';

const Schedule: React.FC = () => {
  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          <IonTitle color='primary'>ApolloCare</IonTitle>
        </IonToolbar>
      </IonHeader>
      <IonContent>
        <div className={styles.card_container}>
          <ScheduleFormContainer />
        </div>
      </IonContent>
    </IonPage>
  );
};

export default Schedule;
