import pandas as pd
import xgboost as xgb
from sklearn.metrics import accuracy_score
from sklearn.model_selection import train_test_split, GridSearchCV

file_path = "C:\\Users\\popgi\\OneDrive\\Documente\\ANUL 4\\LICENTA\\loan_prediction_dataset.csv"
dataframe = pd.read_csv(file_path, delimiter=';')
dataframe.drop('Loan_ID', axis=1, inplace=True)

X = dataframe.drop('Loan_Status', axis=1)
y = dataframe['Loan_Status']
#print(X.columns)
#print(X)
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

model = xgb.XGBClassifier()

# Definirea grilei de hiperparametri pentru XGBoost
hyperparameters = {
    'n_estimators': [30, 50, 100],
    'max_depth': [3, 5, 7],
    'learning_rate': [0.01, 0.05, 0.1]
}

# Căutarea hiperparametrilor optimi folosind GridSearchCV
grid_search = GridSearchCV(model, hyperparameters, cv=5, scoring='accuracy')
grid_search.fit(X_train, y_train)

# Afișarea celor mai buni hiperparametri găsiți
print("Best Parameters:", grid_search.best_params_)

# Predicția utilizând modelul cu cei mai buni hiperparametri
best_model = grid_search.best_estimator_
predictions = best_model.predict(X_test)
accuracy = accuracy_score(y_test, predictions) * 100
print("Acuratețe cu cei mai buni hiperparametri:", accuracy)

best_model.save_model("loan_prediction_model.json")


