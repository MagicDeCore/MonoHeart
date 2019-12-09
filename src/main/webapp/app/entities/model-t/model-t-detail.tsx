import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './model-t.reducer';
import { IModelT } from 'app/shared/model/model-t.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IModelTDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ModelTDetail extends React.Component<IModelTDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { modelTEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            ModelT [<b>{modelTEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{modelTEntity.name}</dd>
            <dt>
              <span id="wrong">Wrong</span>
            </dt>
            <dd>{modelTEntity.wrong}</dd>
            <dt>Part T</dt>
            <dd>
              {modelTEntity.partTS
                ? modelTEntity.partTS.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.name}</a>
                      {i === modelTEntity.partTS.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/model-t" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/model-t/${modelTEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ modelT }: IRootState) => ({
  modelTEntity: modelT.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ModelTDetail);
