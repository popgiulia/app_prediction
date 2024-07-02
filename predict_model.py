import pandas as pd
from flask import Flask, request, jsonify
import joblib

app = Flask(__name__)

# Load the trained model
loaded_model = joblib.load("loan_prediction_model.pkl")

@app.route('/predict', methods=['POST'])
def predict_loan_status():
    data = request.json
    individual_df = pd.DataFrame([data], columns=['Gender', 'Married', 'Education', 'Self_Employed', 'Property_Area',
                                                  'Dependents', 'ApplicantIncome', 'CoapplicantIncome', 'LoanAmount',
                                                  'Loan_Amount_Term', 'Credit_History'])

    # Predicția utilizând modelul încărcat
    prediction = loaded_model.predict(individual_df)

    if prediction[0] == 1:
        result = "Loan will be granted."
    else:
        result = "Loan will not be granted."

    return jsonify({"prediction": result})

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
