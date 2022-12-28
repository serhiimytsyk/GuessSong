import React from 'react';
import Button from '@mui/material/Button';

class Score extends React.Component {
    constructor(props) {
        super(props);
        this.state = {score : props.score, maxScore : props.maxScore, reload : props.reload, restart : props.restart};
    }

    render() {
        return (
            <div>
                <div>Congratulations! Your score is {this.state.score} out of {this.state.maxScore}!</div>
                <Button variant="contained" onClick={this.state.reload}>Load new questions</Button>
                <br></br>
                <Button variant="contained" onClick={this.state.restart}>Start new game</Button>
            </div>
        );
    }
}

export default Score;