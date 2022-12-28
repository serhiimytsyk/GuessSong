import React from 'react';
import TextField from "@mui/material/TextField";
import FormLabel from "@mui/material/FormLabel";
import Button from "@mui/material/Button";

class Question extends React.Component {
    constructor(props) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.state = {question : props.question, value : '',
            correntAnswerUpdater : props.correntAnswerUpdater, wrongAnswerUpdater : props.wrongAnswerUpdater};
    }

    handleChange(event) {
        this.setState({value: event.target.value});
    }

    handleSubmit(event) {
        if (this.state.value === this.state.question.track_name) {
            this.state.correntAnswerUpdater();
        } else {
            this.state.wrongAnswerUpdater();
        }
        event.preventDefault();
    }

    render() {
        return (
            <form  onSubmit={this.handleSubmit}>
              <FormLabel>
                What song is "{this.state.question.line_text}" from?
              </FormLabel>
              <br></br>
              <TextField 
                id="song-input"
                name="Song"
                label="Song"
                type="text"
                value={this.state.value}
                onChange={this.handleChange}/>
              <br></br>
              <Button variant="contained" type="submit" value="Submit">Submit</Button>
            </form >
          );
    }
}

export default Question;